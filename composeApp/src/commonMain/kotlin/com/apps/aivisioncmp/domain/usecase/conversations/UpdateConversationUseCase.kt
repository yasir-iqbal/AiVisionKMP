package com.apps.aivisioncmp.domain.usecase.conversations

import com.apps.aivisioncmp.data.model.Conversation
import com.apps.aivisioncmp.domain.repository.ConversationRepository

class UpdateConversationUseCase(private val repository: ConversationRepository) {

    suspend operator fun invoke(conversation: Conversation) = repository.updateConversation(conversation)

}