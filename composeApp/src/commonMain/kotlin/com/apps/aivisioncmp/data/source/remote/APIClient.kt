package com.apps.aivisioncmp.data.source.remote

import com.apps.aivisioncmp.data.model.GPTRequestParam
import com.apps.aivisioncmp.data.model.ImageGenerationStatus
import com.apps.aivisioncmp.utils.Constants
import com.apps.aivisioncmp.utils.KMPLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.addJsonObject

class APIClient(private val client: HttpClient) {

    fun completeChatWithStream(scope: CoroutineScope,
                               request: GPTRequestParam
    ): Flow<String> = callbackFlow {
        val logger = KMPLogger()
        val job = launch(Dispatchers.IO) {

            kotlin.runCatching {

                val response: HttpResponse =  client.post(COMPLETION_END_POINT){
                   /* headers {
                        append("Authorization", "Bearer ${Constants.GPT_KEY}")
                        append(HttpHeaders.ContentType, "application/json")
                    }*/

                    setBody(
                        buildJsonObject {
                            put("model", request.model)
                            put("stream", request.stream)
                            putJsonArray("messages") {
                                request.messages.forEach {
                                    addJsonObject {
                                        put("role", it.role)
                                        put("content", it.content)
                                    }
                                }
                            }
                        }
                    )
                }

                try{
                val channel = response.bodyAsChannel()

                while (!channel.isClosedForRead) {
                    val line = channel.readUTF8Line() ?: continue
                    logger.error(TAG,"line:${line}")
                    if (line.startsWith("data: ")) {

                        val json = line.removePrefix("data: ").trim()

                        if (json == "[DONE]") break

                        try {
                            val chunk = Json {
                                ignoreUnknownKeys = true
                            }.decodeFromString<StreamChunk>(json)
                            //val chunk = Json.decodeFromString<StreamChunk>(json)
                            val delta = chunk.choices.firstOrNull()?.delta?.content
                            if (!delta.isNullOrEmpty()) {
                                logger.error(TAG,"line:${delta}")
                                trySend(delta)
                                delay(30)
                            }
                        } catch (e: Exception) {
                            logger.error(TAG,"error parsing..")
                            e.printStackTrace()
                            throw Exception(e)
                            // Malformed JSON, skip
                        }
                    } else  {
                        val json = line.trim()
                        trySend(json)
                        delay(30)
                    }
                    if (!scope.isActive) {
                        break
                    }
                }
                }catch (e:Exception){  logger.error(TAG,"api call..")
                    throw Exception(e)}

            }.onFailure {
                logger.error(TAG,"main call")
                it.printStackTrace()
                trySend("Network Failure! Try again.")
                close()
            }
            close()
        }
        awaitClose {
            logger.error(TAG, "Flow closed")
            job.cancel()
        }
        //close()
    }.flowOn(Dispatchers.IO)

    fun generateImage(prompt:String): Flow<ImageGenerationStatus> = callbackFlow<ImageGenerationStatus> {
        val logger = KMPLogger()
        runCatching {
            val response = client.post(GENERATION_END_POINT) {
                setBody(ImageRequest(
                    prompt = prompt,
                    n = 1,
                    size = "512x512",
                    response_format = "b64_json"
                ))
            }
            val result = response.body<ImageResponse>()
            logger.debug(TAG,"response:${result}")
            if (result.data.isNotEmpty()){
                val image = result.data[0]
                logger.debug(TAG,"image:${image.url}")
                trySend(ImageGenerationStatus.Generated(image.url.toString()))
            }else
            {
                trySend(ImageGenerationStatus.GenerationError("Failure!:empty list"))
                close()
            }

        }.onFailure {
            logger.error(TAG,"error:${it.message}")

            trySend(ImageGenerationStatus.GenerationError(it.message.toString()))
        }
        close()

    }.flowOn(Dispatchers.IO)
    companion object{
        private const val TAG = "APIClient"
        const val COMPLETION_END_POINT= "chat/completions"
        const val GENERATION_END_POINT = "images/generations"
    }
}

@Serializable
data class StreamChunk(
    val id: String,
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val delta: Delta
)

@Serializable
data class Delta(
    val content: String? = null
)


@Serializable
data class ImageRequest(
    val prompt: String,
    val n: Int = 1,
    val size: String = "1024x1024",
    val response_format: String = "b64_json" // or "url"
)

@Serializable
data class ImageResponse(
    val created: Long,
    val data: List<ImageData>
)

@Serializable
data class ImageData(
    val b64_json: String? = null,
    val url: String? = null,
    val revised_prompt: String? = null
)