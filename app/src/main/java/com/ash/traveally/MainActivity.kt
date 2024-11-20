package com.ash.traveally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ash.traveally.ui.screens.AddBlogScreen
import com.ash.traveally.ui.screens.BlogScreen
import com.ash.traveally.ui.screens.ChatScreen
import com.ash.traveally.ui.screens.LoginScreen
import com.ash.traveally.ui.screens.MainScreen
import com.ash.traveally.ui.screens.PlaceScreen
import com.ash.traveally.ui.screens.RegisterScreen
import com.ash.traveally.ui.screens.StartPagerScreen
import com.ash.traveally.ui.theme.TraveallyTheme
import com.ash.traveally.utils.Screens.ADD_BLOG_SCREEN
import com.ash.traveally.utils.Screens.BLOG_SCREEN
import com.ash.traveally.utils.Screens.CHAT_SCREEN
import com.ash.traveally.utils.Screens.LOGIN_SCREEN
import com.ash.traveally.utils.Screens.MAIN_SCREEN
import com.ash.traveally.utils.Screens.PLACE_SCREEN
import com.ash.traveally.utils.Screens.REGISTER_SCREEN
import com.ash.traveally.utils.Screens.START_PAGER_SCREEN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraveallyTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN
    ) {
        composable(route = START_PAGER_SCREEN) {
            StartPagerScreen(
                onClick = { navController.navigate(LOGIN_SCREEN) }
            )
        }
        composable(route = LOGIN_SCREEN) {
            LoginScreen(
                onNavigateToMain = { navController.navigate(MAIN_SCREEN) },
                onSignupTextClick = { navController.navigate(REGISTER_SCREEN) }
            )
        }
        composable(route = REGISTER_SCREEN) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(route = MAIN_SCREEN) {
            MainScreen (
                onPlaceClick = {
                    navController.navigate("$PLACE_SCREEN/${it.id}")
                },
                onBlogClick = {
                    navController.navigate("$BLOG_SCREEN/${it.id}")
                },
                onChatClick = {
                    navController.navigate("$CHAT_SCREEN/${it.id}")
                },
                onAddBlogClick = {
                    navController.navigate(ADD_BLOG_SCREEN)
                }
            )
        }
        composable(
            route = "$PLACE_SCREEN/{placeId}",
            arguments = listOf(
                navArgument("placeId") {
                    type = NavType.LongType
                }
            )
        ) {
            PlaceScreen {
                navController.popBackStack()
            }
        }
        composable(
            route = "$BLOG_SCREEN/{blogId}",
            arguments = listOf(
                navArgument("blogId") {
                    type = NavType.LongType
                }
            )
        ) {
            BlogScreen {
                navController.popBackStack()
            }
        }
        composable(
            route = "$CHAT_SCREEN/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.LongType
                }
            )
        ) {
            ChatScreen {
                navController.popBackStack()
            }
        }
        composable(route = ADD_BLOG_SCREEN) {
            AddBlogScreen {
                navController.popBackStack()
            }
        }
    }
}

