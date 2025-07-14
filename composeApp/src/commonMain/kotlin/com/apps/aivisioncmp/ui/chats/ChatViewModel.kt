package com.apps.aivisioncmp.ui.chats

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.aivisioncmp.data.model.ChatMessage
import com.apps.aivisioncmp.data.model.Conversation
import com.apps.aivisioncmp.data.model.GPTMessage
import com.apps.aivisioncmp.data.model.GPTModel
import com.apps.aivisioncmp.data.model.GPTRole
import com.apps.aivisioncmp.data.model.GPTRequestParam
import com.apps.aivisioncmp.data.model.ImageGenerationStatus
import com.apps.aivisioncmp.domain.usecase.app.GetStringResourceExampleUseCase
import com.apps.aivisioncmp.domain.usecase.chat.CompleteChatUseCase
import com.apps.aivisioncmp.domain.usecase.chat.GenerateImageUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.CreateConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.UpdateConversationUseCase
import com.apps.aivisioncmp.domain.usecase.messages.AddMessageUseCase
import com.apps.aivisioncmp.domain.usecase.messages.GetMessagesByLimitUseCase
import com.apps.aivisioncmp.domain.usecase.messages.GetMessagesUseCase
import com.apps.aivisioncmp.domain.usecase.messages.UpdateMessageContentUseCase
import com.apps.aivisioncmp.domain.usecase.messages.UpdateMessageStatusUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.apps.aivisioncmp.utils.ConversationType
import com.apps.aivisioncmp.utils.KMPLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource


private const val DEFAULT_AI_CONTENT =
    "You are an AI bot created by AskAI."
private const val TAG = "ChatViewModel"
class ChatViewModel(private val stringResourceExampleUseCase: GetStringResourceExampleUseCase,
    private val createConversationUseCase: CreateConversationUseCase,
    private val updateConversationUseCase: UpdateConversationUseCase,
    private val addMessageUseCase: AddMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val getMessagesByLimitUseCase: GetMessagesByLimitUseCase,
    private val completeChatUseCase: CompleteChatUseCase,
    private val updateMessageContentUseCase: UpdateMessageContentUseCase,
    private val updateMessageStatusUseCase: UpdateMessageStatusUseCase,
    private val generateImageUseCase: GenerateImageUseCase,

): ViewModel() {

    private var messageJob: Job? = null
    private val apiScope = CoroutineScope(Dispatchers.IO)
    private var apiJob: Job? = null
    private val _messages: MutableStateFlow<List<ChatMessage>> = MutableStateFlow(emptyList())
    val messages = _messages.asStateFlow()
    private val _examples: MutableStateFlow<List<StringResource>> = MutableStateFlow(emptyList())
    val examples = _examples.asStateFlow()
    private val _displayType: MutableStateFlow<DisplayType> = MutableStateFlow(DisplayType.EXAMPLE)
    val displayType get() = _displayType.asStateFlow()
    private val _currentConversationType: MutableStateFlow<ConversationType> = MutableStateFlow(ConversationType.TEXT)
    val currentConversationType get() = _currentConversationType.asStateFlow()
    private val _isAiProcessing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAiProcessing: StateFlow<Boolean> = _isAiProcessing.asStateFlow()
    val title = mutableStateOf("")
    private var recentConversationId:Long = 0
    private var recentMessageId:Long = 0
    private var content =""
    private var prompt =""
    private val logger = KMPLogger()

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }
    fun initWithArg(data: ChatData)
    {
        logger.debug(TAG,"$data")
        data.chatId?.let {
            recentConversationId = it
        }
        data.title?.let {
            title.value = it
        }
        _currentConversationType.value = ConversationType.valueOf(data.conversationType)

        if (recentConversationId <= 0)
        {
            _examples.value = stringResourceExampleUseCase()
        }


        if (recentConversationId>0)
        {
            loadMessages(recentConversationId)
        }

        logger.debug(TAG,"${recentConversationId},${currentConversationType.value}")

    }

    private fun loadMessages(chatId:Long)
    {
        logger.debug(TAG,"load msges: ${recentConversationId},${currentConversationType.value}")
        messageJob = CoroutineScope(Dispatchers.IO).launch {
            val messageStream =getMessagesUseCase(chatId,Dispatchers.IO)
            messageStream.collect{

                _displayType.value = if(it.isEmpty()) DisplayType.EXAMPLE else DisplayType.MESSAGE
                _messages.value = it
                logger.debug(TAG,"$it")

            }
        }
    }

    fun cancelMessageJob(){
        messageJob?.cancel()
    }

    fun sendMessage(text:String)
    {
        viewModelScope.launch(Dispatchers.Default) {

            if (recentConversationId < 1)
            {
                recentConversationId = createConversationUseCase(Conversation(title = text, type = _currentConversationType.value.name))
                logger.debug(TAG,"new con: ${recentConversationId},$currentConversationType")
                loadMessages(recentConversationId)
            }
            addMessageUseCase(ChatMessage(recentChatId = recentConversationId, role = GPTRole.USER.value, content = text, type = _currentConversationType.value.name))
           // messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.USER.value, content = text, type = _currentConversationType.value.name))

            prompt = text
            recentMessageId =0
            if (_currentConversationType.value == ConversationType.TEXT)
            {
                kotlin.runCatching {
                runChatAIApi(text)
                }.onFailure { it.printStackTrace() }
            }else{
                runImageGenerateApi(text)
            }

        }
    }

    private suspend fun runChatAIApi(prompt:String){

        //val history = messageRepository.getMessages(recentConversationId,2)
        val history = getMessagesByLimitUseCase(recentConversationId,2)
        val reqMessages: MutableList<GPTMessage> = mutableListOf(
            GPTMessage(
                role = GPTRole.SYSTEM.value,
                content = DEFAULT_AI_CONTENT
            )
        )
        if (history.isNotEmpty()){
            history.reversed().forEach { obj->
                reqMessages.add(GPTMessage(obj.content,GPTRole.values().first { it.value == obj.role }.value))
            }
        }

        val flow: Flow<String> = completeChatUseCase(
            scope = apiScope,
            GPTRequestParam(
                messages = reqMessages.toList(),
                model = GPTModel.gpt4mini.model
            )
        )
        content = ""
        apiJob = apiScope.launch(coroutineExceptionHandler) {
            _isAiProcessing.value = true
            flow.collect{
                content= "${content}${it}"
                logger.error(TAG,"content:${content}")

                if (recentMessageId>0)
                {
                   // logger.error(TAG,"content:${content}")
                    updateMessageContentUseCase(recentMessageId,content,"")

                }else{
                    recentMessageId = addMessageUseCase(ChatMessage(recentChatId = recentConversationId, role = GPTRole.ASSISTANT.value, content = content, type = ConversationType.TEXT.name))
                    logger.debug(TAG,"create msg: ${recentMessageId}")
                }
            }

            _isAiProcessing.value = false
            updateConversationUseCase(Conversation(id = recentConversationId, title = prompt,content = if (content.length<100) content else content.substring(0..99)))
        }
    }

    fun stopAIContentGeneration()
    {
        viewModelScope.launch {
            apiJob?.cancel()
            _isAiProcessing.value = false
        }
    }

    private fun runImageGenerateApi(message: String)
    {
        val imageFlow = generateImageUseCase(message)

        content = ""
        apiJob = apiScope.launch(coroutineExceptionHandler) {
            _isAiProcessing.value = true

            imageFlow.collect{
                when(it){
                    is ImageGenerationStatus.Generated->{
                        _isAiProcessing.value = false

                        if (recentMessageId<=0)
                        {
                            recentMessageId = addMessageUseCase(ChatMessage(recentChatId = recentConversationId,  content = content, type = ConversationType.IMAGE.name, url = it.base64))

                        }else{
                            updateMessageContentUseCase(recentMessageId,content,it.base64)
                        }
                       // messageRepository.updateStatus(recentMessageId, DownloadStatusEnum.DOWNLOADING.value)
                        updateConversationUseCase(Conversation(id = recentConversationId, title = prompt,content = it.base64))


                    }
                    is ImageGenerationStatus.GenerationError->{
                        _isAiProcessing.value = false
                        if (recentMessageId<=0)
                        {
                            recentMessageId = addMessageUseCase(ChatMessage(recentChatId = recentConversationId, content = "Failure! Try again.", type = ConversationType.IMAGE.name, url = ""))

                        }else{
                            updateMessageContentUseCase(recentMessageId,"Failure! Try again.","")
                        }
                        updateConversationUseCase(Conversation(id = recentConversationId, title = prompt,content = "Failure! Try again."))

                    }
                    is ImageGenerationStatus.Downloading->{

                    }
                    is ImageGenerationStatus.Completed->{

                    }
                    is ImageGenerationStatus.DownloadError->{

                    }
                }
            }
        }

    }

    fun getGPTModel() = GPTModel.gpt4mini

    override fun onCleared() {
        super.onCleared()
        messageJob?.cancel()
    }

}

@Serializable
data class ChatData(val chatId:Long?=null,val title:String?=null,val conversationType: String = ConversationType.TEXT.name)
enum class DisplayType {
    EXAMPLE,MESSAGE
}