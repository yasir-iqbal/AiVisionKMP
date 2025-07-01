package com.apps.aivisioncmp.domain.usecase.chat
import com.apps.aivisioncmp.data.model.GPTRequestParam
import com.apps.aivisioncmp.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope

class CompleteChatUseCase(private val repository: ChatRepository) {

    operator fun invoke(scope: CoroutineScope,
                                request: GPTRequestParam
    ) = repository.textCompletionsWithStream(scope,request)

}