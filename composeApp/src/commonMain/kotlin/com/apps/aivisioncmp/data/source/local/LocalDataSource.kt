package com.apps.aivisioncmp.data.source.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.apps.aivisioncmp.data.model.ChatMessage
import com.apps.aivisioncmp.data.model.Conversation
import com.apps.aivisioncmp.database.ConversationEntity
import com.apps.aivisioncmp.database.MessageEntity
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

class LocalDataSource(private val db: AppDatabaseHelper) {

    suspend fun addConversation(conversation: Conversation) = db.visionDAO.insertConversation(conversation.title,conversation.type,conversation.content,"${Clock.System.now().toEpochMilliseconds()}")
    suspend fun getAllConversations():List<Conversation> = db.visionDAO.getAllConversations().executeAsList().map { it.toConversation() }
    suspend fun searchConversation(query:String):List<Conversation> = db.visionDAO.searchConversation(query).executeAsList().map { it.toConversation() }
    suspend fun deleteConversation(id:Long) = db.visionDAO.deleteConversation(id)
    suspend fun deleteAllConversations() = db.visionDAO.deleteAllConversations()
    suspend fun updateConversation(conversation: Conversation) = db.visionDAO.updateConversation(conversation.title,conversation.content,conversation.id)

    suspend fun addMessage(chatMessage: ChatMessage) = db.visionDAO.insertMessage(chatMessage.recentChatId,chatMessage.role,chatMessage.content,chatMessage.type,chatMessage.url,chatMessage.status.toLong(),"${Clock.System.now().toEpochMilliseconds()}")
    fun getAllMessages(conversationId:Long,context: CoroutineContext): Flow<List<ChatMessage>> = db.visionDAO.getAllMessages(conversationId).asFlow().mapToList(context).map { it.map { it.toChatMessage() } }
    suspend fun deleteAllMessages(conversationId:Long) = db.visionDAO.deleteAllMessages(conversationId)
    suspend fun updateMessageStatus(id:Long,status:Int) = db.visionDAO.updateMessageStatus(status.toLong(),id)
    suspend fun updateMessageContentAndUrl(id:Long,content:String,url:String) = db.visionDAO.updateMessageContentAndUrl(content,url,id)
    suspend fun getMessagesByLimit(conversationId:Long,limit:Long): List<ChatMessage> = db.visionDAO.getMessagesByLimit(conversationId,limit).executeAsList().map { it.toChatMessage() }
    suspend fun getLastInsertedRecordId():Long = db.visionDAO.lastInsertId().executeAsOne()

    private fun ConversationEntity.toConversation() = Conversation(id,title ?: "",type?:"",content ?:"",created_at ?:"")
    private fun MessageEntity.toChatMessage() = ChatMessage(id,chat_id?:0,role?:"",content?:"",type?:"",url?:"",status.toInt(),created_at?:"")
}