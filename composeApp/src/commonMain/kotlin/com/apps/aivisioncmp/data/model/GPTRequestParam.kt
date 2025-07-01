package com.apps.aivisioncmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GPTRequestParam(
    val temperature: Double = 0.9,
    var stream: Boolean = true,
    val model: String = GPTModel.gpt4.model,
    val messages: List<GPTMessage> = emptyList(),
)

