package com.apps.aivisioncmp.domain.usecase.conversations

import com.apps.aivisioncmp.data.model.Conversation
import com.apps.aivisioncmp.domain.repository.ConversationRepository

class GetAllConversationUseCase(private val repository: ConversationRepository) {

    suspend operator fun invoke() = repository.getAllConversation()

}