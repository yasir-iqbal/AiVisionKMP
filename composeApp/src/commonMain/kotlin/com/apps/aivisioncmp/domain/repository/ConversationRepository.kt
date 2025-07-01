package com.apps.aivisioncmp.domain.repository

import com.apps.aivisioncmp.data.model.Conversation

interface ConversationRepository {
    suspend fun addConversation(conversation: Conversation):Long
    suspend fun getAllConversation(): List<Conversation>
    suspend fun searchConversation(query:String): List<Conversation>
    suspend fun deleteConversation(chatId: Long)
    suspend fun deleteAllConversations()
    suspend fun updateConversation(conversation: Conversation)
}