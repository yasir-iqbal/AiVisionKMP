package com.apps.aivisioncmp.domain.usecase.messages

import com.apps.aivisioncmp.data.model.ChatMessage
import com.apps.aivisioncmp.domain.repository.MessageRepository

class AddMessageUseCase(private val repository: MessageRepository) {

    suspend operator fun invoke(message: ChatMessage) = repository.addMessage(message)

}