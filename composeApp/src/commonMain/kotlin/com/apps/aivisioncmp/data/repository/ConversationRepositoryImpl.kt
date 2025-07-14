package com.apps.aivisioncmp.data.repository

import com.apps.aivisioncmp.data.model.Conversation
import com.apps.aivisioncmp.data.source.local.LocalDataSource
import com.apps.aivisioncmp.domain.repository.ConversationRepository

class ConversationRepositoryImpl(private val localDataSource: LocalDataSource):ConversationRepository {
    override suspend fun addConversation(conversation: Conversation):Long {
        localDataSource.addConversation(conversation)
        return localDataSource.getLastConversationId().maxId ?: 0L
    }

    override suspend fun getAllConversation(): List<Conversation> = localDataSource.getAllConversations()

    override suspend fun searchConversation(query: String): List<Conversation> = localDataSource.searchConversation(query)

    override suspend fun deleteConversation(chatId: Long) = localDataSource.deleteConversation(chatId)

    override suspend fun deleteAllConversations() = localDataSource.deleteAllConversations()

    override suspend fun updateConversation(conversation: Conversation) = localDataSource.updateConversation(conversation)
}