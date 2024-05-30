package kkm.test.compose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Search : Screen(route = "search_screen", "Search", Icons.Default.Search)
    object Bookmark : Screen(route = "bookmark_screen", "Bookmark", Icons.Default.Favorite)
}