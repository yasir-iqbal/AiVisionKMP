package com.apps.aivisioncmp.domain.usecase.chat
import com.apps.aivisioncmp.data.model.GPTRequestParam
import com.apps.aivisioncmp.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope

class GenerateImageUseCase(private val repository: ChatRepository) {

    operator fun invoke(prompt:String
    ) = repository.generateImage(prompt)

}