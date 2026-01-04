package com.code.machinecoding.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.code.machinecoding.ui.chat.ChatListScreen
import com.code.machinecoding.ui.chat.ChatScreen
import com.code.machinecoding.ui.home.HomeScreen
import com.code.machinecoding.ui.viewmodel.ChatViewModel
import com.code.machinecoding.utils.HideStatusBar
import com.code.machinecoding.utils.NavRoutes
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost() {


    HideStatusBar()
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,

        enterTransition = {
            slideInVertically (
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = 700,
                    easing = LinearOutSlowInEasing
                )
            ) + fadeIn()
        },

        exitTransition = {
            slideOutVertically(
                targetOffsetY = { -it / 3 },
                animationSpec = tween(700)
            ) + fadeOut()
        },

        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 3 },
                animationSpec = tween(700)
            ) + fadeIn()
        },

        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(700)
            ) + fadeOut()
        }
    ) {

        composable(NavRoutes.HOME) {
            HomeScreen(
                onChatWithUs = {
                    navController.navigate(NavRoutes.CHAT_LIST)
                }
            )
        }

        composable(NavRoutes.CHAT_LIST) {
            ChatListScreen(
                onOpenChat = {
                    navController.navigate(NavRoutes.CHAT_ROOT)
                }
            )
        }

        navigation(
            startDestination = NavRoutes.CHAT_SCREEN,
            route = NavRoutes.CHAT_ROOT
        ) {
            composable(NavRoutes.CHAT_SCREEN) {
                val vm: ChatViewModel = hiltViewModel()
                ChatScreen(viewModel = vm)
            }
        }
    }
}

