package com.example.animeapp.ui.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.animeapp.ui.navigation.BottomBarItem
import com.example.animeapp.ui.navigation.graphs.HomeNavGraph
import com.example.animeapp.R

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    HomeScreenContent(navController)
}

@Composable
fun HomeScreenContent(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        HomeNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarItem.Search,
        BottomBarItem.Favorites
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.onSecondary
        ) {
            screens.forEach { item ->
                AddItem(
                    item = item,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    item: BottomBarItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.caption
            )
        },
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = stringResource(R.string.bottom_navigation_description)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == item.route
        } == true,
        selectedContentColor = Color(0xFF4C4ECB),
        unselectedContentColor = Color(0xFF4C4ECB).copy(
            0.4f
        ),
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}
