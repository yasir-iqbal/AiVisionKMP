package com.apps.aivisioncmp.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Conversation : Screen("conversation_screen")
    object Chat : Screen("chat_screen")
    object Language : Screen("language_screen")
}