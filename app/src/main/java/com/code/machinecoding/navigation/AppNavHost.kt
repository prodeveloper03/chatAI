package com.code.machinecoding.navigation

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.code.machinecoding.ui.chat.ChatListScreen
import com.code.machinecoding.ui.chat.ChatScreen
import com.code.machinecoding.ui.chat.FullScreenImageViewer
import com.code.machinecoding.ui.home.HomeScreen
import com.code.machinecoding.ui.viewmodel.ChatViewModel
import com.code.machinecoding.utils.HideStatusBar
import com.code.machinecoding.utils.NavRoutes
import com.code.machinecoding.utils.NavRoutes.IMAGE_VIEWER
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost() {

    val NAV_ANIM_DURATION = 500
    val NAV_POP_ANIM_DURATION = 400

    HideStatusBar()
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,

        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(
                    durationMillis = NAV_ANIM_DURATION,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(tween(NAV_ANIM_DURATION))
        },

        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 4 },
                animationSpec = tween(
                    durationMillis = NAV_ANIM_DURATION,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(tween(NAV_ANIM_DURATION))
        },

        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 4 },
                animationSpec = tween(
                    durationMillis = NAV_POP_ANIM_DURATION,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(tween(NAV_POP_ANIM_DURATION))
        },

        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(
                    durationMillis = NAV_POP_ANIM_DURATION,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(tween(NAV_POP_ANIM_DURATION))
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

                ChatScreen(
                    viewModel = vm,
                    onImageClick = { fullImagePath ->
                        val encodedPath = Uri.encode(fullImagePath)
                        navController.navigate("${NavRoutes.IMAGE_VIEWER}/$encodedPath")
                    }
                )
            }
        }



        composable(
            route = "$IMAGE_VIEWER/{imagePath}",
            arguments = listOf(
                navArgument("imagePath") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            FullScreenImageViewer(
                imagePath = backStackEntry.arguments?.getString("imagePath")!!,
                onBack = { navController.popBackStack() }
            )
        }

    }
}



