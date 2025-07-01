package com.apps.aivisioncmp.data.repository

import com.apps.aivisioncmp.data.model.GPTRequestParam
import com.apps.aivisioncmp.data.source.remote.APIClient
import com.apps.aivisioncmp.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class ChatRepositoryImpl(private val apiClient: APIClient):ChatRepository {
    override fun textCompletionsWithStream(
        scope: CoroutineScope,
        request: GPTRequestParam
    ): Flow<String> = apiClient.completeChatWithStream(scope,request)
}