package com.apps.aivisioncmp.domain.usecase.conversations

import com.apps.aivisioncmp.domain.repository.ConversationRepository


class DeleteAllConversationUseCase(private val repository: ConversationRepository) {

    suspend operator fun invoke() = repository.deleteAllConversations()

}