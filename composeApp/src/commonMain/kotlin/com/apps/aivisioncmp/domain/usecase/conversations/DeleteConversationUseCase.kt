package com.apps.aivisioncmp.domain.usecase.conversations

import com.apps.aivisioncmp.domain.repository.ConversationRepository

class DeleteConversationUseCase(private val repository: ConversationRepository) {

    suspend operator fun invoke(id: Long) = repository.deleteConversation(id)

}