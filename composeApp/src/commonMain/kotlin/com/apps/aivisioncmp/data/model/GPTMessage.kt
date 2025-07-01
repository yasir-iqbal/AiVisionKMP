package com.apps.aivisioncmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GPTMessage(
    val content: String = "",
    val role: String = GPTRole.USER.value,
)

