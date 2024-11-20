package com.ash.traveally.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Queue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ash.traveally.models.Blog
import com.ash.traveally.models.Place
import com.ash.traveally.models.User
import com.ash.traveally.ui.components.etc.TopAppBar
import com.ash.traveally.ui.theme.MontserratAlternates
import com.ash.traveally.utils.Screens.BLOGS_SCREEN
import com.ash.traveally.utils.Screens.CHATS_SCREEN
import com.ash.traveally.utils.Screens.HOME_SCREEN
import com.ash.traveally.utils.Screens.PROFILE_SCREEN

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    onPlaceClick: (Place) -> Unit,
    onBlogClick: (Blog) -> Unit,
    onAddBlogClick: () -> Unit,
    onChatClick: (User) -> Unit
) {
    val navController = rememberNavController()
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.route)
                        },
                        label = { Text(text = item.title, fontFamily = MontserratAlternates) },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = HOME_SCREEN
            ) {
                composable(route = HOME_SCREEN) {
                    HomeScreen { onPlaceClick(it) }
                }
                composable(route = PROFILE_SCREEN) {
                    ProfileScreen()
                }
                composable(route = BLOGS_SCREEN) {
                    BlogsScreen(
                        onItemClick = { onBlogClick(it) },
                        onAddBlogClick = { onAddBlogClick() }
                    )
                }
                composable(route = CHATS_SCREEN) {
                    ChatsScreen { onChatClick(it) }
                }
            }
        }
    }
}

val navItems = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = HOME_SCREEN
    ),
    BottomNavigationItem(
        title = "Blogs",
        selectedIcon = Icons.Filled.Queue,
        unselectedIcon = Icons.Outlined.Queue,
        route = BLOGS_SCREEN
    ),
    BottomNavigationItem(
        title = "Chats",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        route = CHATS_SCREEN
    ),
    BottomNavigationItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = PROFILE_SCREEN
    )
)

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)