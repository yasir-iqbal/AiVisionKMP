package com.apps.aivisioncmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.compose_multiplatform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Propane
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apps.aivisioncmp.di.appModules
import com.apps.aivisioncmp.di.networkModule
import com.apps.aivisioncmp.di.repositoryModule
import com.apps.aivisioncmp.di.useCaseModule
import com.apps.aivisioncmp.di.viewModelModule
import com.apps.aivisioncmp.ui.conversations.ConversationScreen
import com.apps.aivisioncmp.ui.navigation.NavigationGraph
import com.apps.aivisioncmp.ui.theme.AIVisionTheme
import com.apps.aivisioncmp.ui.welcome.WelcomeScreen
import org.koin.compose.KoinApplication


data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("home", "Home", Icons.Default.Home),
    BottomNavItem("detail", "Detail", Icons.Default.Info),
            BottomNavItem("profile", "profile", Icons.Default.Propane)
)
val bottomBarHiddenRoutes = listOf("home", "login")
@Composable
@Preview
fun App() {
    KoinApplication (application = {
        modules(*appModules)
    }){
        AIVisionTheme() {
            AppNavigation()
            //ConversationScreen(navigateToChat = {_,_->})
            //WelcomeScreen()
            //    AppNavigationWithBottomBar()
        }
    }
    /*AIVisionTheme() {
        WelcomeScreen()
    //    AppNavigationWithBottomBar()
    }*/
  /*  AppAppTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }*/
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {}
    ) { innerPadding ->
        NavigationGraph(navController,innerPadding)
    }
}




@Composable
fun AppNavigationWithBottomBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val shouldShowBottomBar = currentRoute !in bottomBarHiddenRoutes
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar){
            CustomBottomBar(
                currentRoute = currentRoute,
                onItemSelected = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
           // composable("home") { HomeScreen(onNavigateToDetail = {navController.navigate("detail") }) }
           // composable("detail") { DetailScreen(onBack = {}) }
           // composable("profile") { ProfileScreen(onNavigateToDetail = {}) }
        }
    }
}

@Composable
fun CustomBottomBar(
    currentRoute: String?,
    onItemSelected: (BottomNavItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = item.route == currentRoute
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onItemSelected(item) }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (isSelected) Color.Blue else Color.Gray
                )
                Text(
                    text = item.label,
                    color = if (isSelected) Color.Blue else Color.Gray
                )
            }
        }
    }
}