package com.code.machinecoding.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.code.machinecoding.ui.chat.ChatScreen
import com.code.machinecoding.ui.home.HomeScreen
import com.code.machinecoding.ui.viewmodel.ChatViewModel
import com.code.machinecoding.utils.NavRoutes

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {

        composable(NavRoutes.HOME) {
            HomeScreen(
                onOpenChat = { navController.navigate(NavRoutes.CHAT_ROOT) }
            )
        }

        navigation(
            startDestination = NavRoutes.CHAT_MAIN,
            route = NavRoutes.CHAT_ROOT
        ) {

            composable(NavRoutes.CHAT_MAIN) {
                val chatViewModel: ChatViewModel = hiltViewModel()
                ChatScreen(viewModel = chatViewModel)
            }

//            // Optional: Chat details / fullscreen image, etc.
//            composable(NavRoutes.CHAT_DETAILS) {
//                val chatViewModel: ChatViewModel = hiltViewModel()
//                ChatDetailsScreen(viewModel = chatViewModel)
//            }
        }
    }
}
