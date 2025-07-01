package com.apps.aivisioncmp.domain.usecase.messages

import com.apps.aivisioncmp.domain.repository.MessageRepository
import kotlin.coroutines.CoroutineContext

class GetMessagesByLimitUseCase(private val repository: MessageRepository) {

    suspend operator fun invoke(recentChatId: Long,limit:Int) = repository.getMessages(recentChatId,limit)

}