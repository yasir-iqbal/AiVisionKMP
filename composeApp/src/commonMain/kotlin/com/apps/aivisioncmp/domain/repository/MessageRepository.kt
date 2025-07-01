package com.apps.aivisioncmp.domain.repository
import com.apps.aivisioncmp.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface MessageRepository {
    fun getMessages(recentChatId: Long,context: CoroutineContext): Flow<List<ChatMessage>>
    suspend fun addMessage(message: ChatMessage):Long
    suspend fun deleteMessages(recentChatId: Long)
    suspend fun updateStatus(messageId:Long,status:Int)
    suspend fun updateContent(messageId:Long,content:String,url:String)
    suspend fun getMessages(recentChatId: Long,limit:Int): List<ChatMessage>
}
