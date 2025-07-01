package com.apps.aivisioncmp.domain.usecase.messages

import com.apps.aivisioncmp.domain.repository.MessageRepository

class UpdateMessageContentUseCase(private val repository: MessageRepository) {

    suspend operator fun invoke(messageId:Long,content:String,url:String) = repository.updateContent(messageId,content,url)

}