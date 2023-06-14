package com.example.animeapp.presentation.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.animeapp.presentation.ui.character.CharacterScreen
import com.example.animeapp.presentation.ui.navigation.BottomBarItem
import com.example.animeapp.presentation.ui.detail.DetailScreen
import com.example.animeapp.presentation.ui.favorites.FavoritesScreen
import com.example.animeapp.presentation.ui.search.SearchScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarItem.Search.route
    ) {
        composable(route = BottomBarItem.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(route = BottomBarItem.Favorites.route) {
            FavoritesScreen(navController = navController)
        }
        composable(route = "detail") {
            DetailScreen(navController = navController)
        }
        composable(route = "character") {
            CharacterScreen(navController = navController)
        }
    }
}
