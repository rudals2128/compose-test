package kkm.test.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kkm.test.compose.ui.view.BookmarScreen
import kkm.test.compose.ui.view.SearchScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(route = Screen.Search.route) {
            SearchScreen()
        }
        composable(route = Screen.Bookmark.route) {
            BookmarScreen()
        }
    }
}