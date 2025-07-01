package com.apps.aivisioncmp.domain.usecase.messages

import com.apps.aivisioncmp.domain.repository.MessageRepository
import kotlin.coroutines.CoroutineContext

class GetMessagesUseCase(private val repository: MessageRepository) {

    operator fun invoke(recentChatId: Long,context: CoroutineContext) = repository.getMessages(recentChatId,context)

}