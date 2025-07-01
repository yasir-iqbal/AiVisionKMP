package com.apps.aivisioncmp.domain.usecase.messages

import com.apps.aivisioncmp.domain.repository.MessageRepository

class DeleteMessagesUseCase(private val repository: MessageRepository) {

    suspend operator fun invoke(recentChatId: Long) = repository.deleteMessages(recentChatId)

}