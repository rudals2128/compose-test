package kkm.test.compose.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kkm.test.compose.ui.navigation.BottomNavGraph
import kkm.test.compose.ui.navigation.Screen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    var bottomNavigationTitle by remember { mutableStateOf("Search") }

    Scaffold(
        topBar = {
            TopBar(title = bottomNavigationTitle)
        },
        bottomBar = { BottomBar(navController = navController) {
            bottomNavigationTitle = it
        }
        }
    ) {
        BottomNavGraph(navController = navController)
    }

}

@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.h6, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(
                    Alignment.CenterHorizontally
                ))
        },
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = AppBarDefaults.TopAppBarElevation
    )
}

@Composable
fun BottomBar(navController: NavHostController, onTabSelected: (String) -> Unit) {
    val screens = listOf(
        Screen.Search,
        Screen.Bookmark
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,
                onTabSelected
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    onTabSelected: (String) -> Unit
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
            onTabSelected(screen.title)
        }

    )
}