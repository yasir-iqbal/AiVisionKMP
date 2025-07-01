package com.apps.aivisioncmp.data.model

enum class GPTModel(val model: String, val maxTokens: Int) {
    gpt4("gpt-4", 8000),
    gpt4mini("gpt-4o-mini", 16000),
    gpt4Turbo("gpt-4o", 16000),
    gpt4Vision("gpt-4o", 8000)
}