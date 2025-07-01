package com.apps.aivisioncmp.domain.repository

import com.apps.aivisioncmp.data.model.GPTRequestParam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun textCompletionsWithStream(scope: CoroutineScope, request: GPTRequestParam): Flow<String>
}