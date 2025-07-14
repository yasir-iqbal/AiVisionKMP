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
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import com.apps.aivisioncmp.ui.chats.ChatData
import com.apps.aivisioncmp.ui.chats.ChatScreen
import com.apps.aivisioncmp.ui.conversations.ConversationScreen
import com.apps.aivisioncmp.ui.welcome.WelcomeScreen
import com.apps.aivisioncmp.utils.KMPLogger
import kotlinx.serialization.json.Json

@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {

    NavHost(navController = navController, startDestination = Screen.Welcome,  modifier = Modifier.padding(innerPadding),
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }) {
        composable<Screen.Welcome> {
            WelcomeScreen(navigateToConversation = {
                navController.navigate(Screen.Conversation)

            })
            //HomeScreen(onNavigateToDetail = { navController.navigate("detail") })
        }
        composable<Screen.Conversation> {
            ConversationScreen(navigateToChat = { id, type ->
                navController.navigate(Screen.Chat(id,type))
            })
            //DetailScreen(onBack = { navController.popBackStack() })
        }

        composable<Screen.Chat>{
            val chat:Screen.Chat = it.toRoute()
            val logger = KMPLogger()
            logger.error("Data",":${chat}")
            ChatScreen(navigateToBack = {
                navController.popBackStack()
            }, data = ChatData(chatId = chat.chatId, conversationType = chat.type))
        }
    }

}