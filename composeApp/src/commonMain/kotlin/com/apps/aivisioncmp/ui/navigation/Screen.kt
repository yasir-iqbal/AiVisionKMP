package com.apps.aivisioncmp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    data object Welcome : Screen("welcome_screen")
    @Serializable
    data object Conversation : Screen("conversation_screen")
    @Serializable
    data class Chat(val chatId:Long?,val type:String) : Screen("chat_screen")
    @Serializable
    data object Language : Screen("language_screen")
}