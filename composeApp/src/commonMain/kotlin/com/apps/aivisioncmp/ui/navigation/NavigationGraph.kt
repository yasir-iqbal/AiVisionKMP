package com.apps.aivisioncmp.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.savedstate.SavedState
import com.apps.aivisioncmp.ui.chats.ChatData
import com.apps.aivisioncmp.ui.chats.ChatScreen
import com.apps.aivisioncmp.ui.conversations.ConversationScreen
import com.apps.aivisioncmp.ui.welcome.WelcomeScreen
import kotlinx.serialization.json.Json

@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {

    NavHost(navController = navController, startDestination = Screen.Welcome.route,  modifier = Modifier.padding(innerPadding),
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navigateToConversation = {
                navController.navigate(Screen.Conversation.route)

            })
            //HomeScreen(onNavigateToDetail = { navController.navigate("detail") })
        }
        composable(Screen.Conversation.route) {
            ConversationScreen(navigateToChat = { id, type ->

                val data = ChatData(chatId =  id, conversationType = type)
                val json = Json.encodeToString(data)
                navController.navigate("${Screen.Chat.route}/$json")
            })
            //DetailScreen(onBack = { navController.popBackStack() })
        }

        composable(route= "${Screen.Chat.route}/{data}", arguments = listOf(navArgument("data") { type = NavType.StringType })) {
            var data = ChatData()

            val userId = it.arguments?.ge
            it.arguments?.get("data")?.let {json->
                if (json.isNotEmpty())
                {
                    data = Json.decodeFromString<ChatData>(json)
                }
            }
            ChatScreen(navigateToBack = {
                navController.popBackStack()
            }, data = data)
        }
    }

}