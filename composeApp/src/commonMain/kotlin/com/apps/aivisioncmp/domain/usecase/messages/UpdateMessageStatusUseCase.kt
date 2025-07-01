package com.apps.aivisioncmp.domain.usecase.messages

import com.apps.aivisioncmp.domain.repository.MessageRepository

class UpdateMessageStatusUseCase(private val repository: MessageRepository) {

    suspend operator fun invoke(messageId:Long,status:Int) = repository.updateStatus(messageId,status)

}