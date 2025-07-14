package com.apps.aivisioncmp.domain.repository

import com.apps.aivisioncmp.data.model.GPTRequestParam
import com.apps.aivisioncmp.data.model.ImageGenerationStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun textCompletionsWithStream(scope: CoroutineScope, request: GPTRequestParam): Flow<String>
    fun generateImage(prompt:String): Flow<ImageGenerationStatus>
}