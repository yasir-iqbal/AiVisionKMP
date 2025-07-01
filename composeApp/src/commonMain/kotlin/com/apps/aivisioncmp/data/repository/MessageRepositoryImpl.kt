package com.apps.aivisioncmp.data.repository

import com.apps.aivisioncmp.data.model.ChatMessage
import com.apps.aivisioncmp.data.source.local.LocalDataSource
import com.apps.aivisioncmp.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class MessageRepositoryImpl(private val localDataSource: LocalDataSource):MessageRepository {

    override fun getMessages(recentChatId: Long,context: CoroutineContext): Flow<List<ChatMessage>> = localDataSource.getAllMessages(recentChatId,context)

    override suspend fun getMessages(recentChatId: Long, limit: Int): List<ChatMessage> = localDataSource.getMessagesByLimit(recentChatId,limit.toLong())

    override suspend fun addMessage(message: ChatMessage):Long {
        localDataSource.addMessage(message)
        return localDataSource.getLastInsertedRecordId()
    }

    override suspend fun deleteMessages(recentChatId: Long) = localDataSource.deleteAllMessages(recentChatId)

    override suspend fun updateStatus(messageId: Long, status: Int) = localDataSource.updateMessageStatus(messageId,status)
    override suspend fun updateContent(messageId: Long, content: String, url: String) = localDataSource.updateMessageContentAndUrl(messageId,content,url)

}