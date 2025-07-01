package com.apps.aivisioncmp.data.source.remote

import com.apps.aivisioncmp.data.model.GPTRequestParam
import com.apps.aivisioncmp.utils.Constants
import io.ktor.client.HttpClient
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
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
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                val response: HttpResponse =  client.post(COMPLETION_END_POINT){
                    headers {
                        append("Authorization", "Bearer ${Constants.GPT_KEY}")
                        append(HttpHeaders.ContentType, "application/json")
                    }

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

                    if (line.startsWith("data: ")) {
                        val json = line.removePrefix("data: ").trim()

                        if (json == "[DONE]") break

                        try {
                            val chunk = Json.decodeFromString<StreamChunk>(json)
                            val delta = chunk.choices.firstOrNull()?.delta?.content
                            if (!delta.isNullOrEmpty()) {
                                trySend(delta)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            // Malformed JSON, skip
                        }
                    }
                    if (!scope.isActive) {
                        break
                    }
                }
                }catch (e:Exception){ throw Exception(e)}

            }.onFailure {
                it.printStackTrace()
                trySend("Network Failure! Try again.")
                close()
            }
        }
        close()
    }.flowOn(Dispatchers.IO)

    companion object{
        private const val TAG = "APIClient"
        const val COMPLETION_END_POINT= "${Constants.BASE_URL}chat/completions"
    }
}

@kotlinx.serialization.Serializable
data class StreamChunk(
    val id: String,
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val delta: Delta
)

@kotlinx.serialization.Serializable
data class Delta(
    val content: String? = null
)