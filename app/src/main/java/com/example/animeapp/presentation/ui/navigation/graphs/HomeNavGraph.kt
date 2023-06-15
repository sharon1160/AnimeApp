package com.example.animeapp.presentation.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.animeapp.presentation.ui.character.CharacterScreen
import com.example.animeapp.presentation.ui.detail.DetailScreen
import com.example.animeapp.presentation.ui.favorites.FavoritesScreen
import com.example.animeapp.presentation.ui.search.SearchScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = "search"
    ) {
        composable(route = "search") {
            SearchScreen(navController = navController)
        }
        composable(route = "favorites") {
            FavoritesScreen(navController = navController)
        }
        composable(route = "detail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            DetailScreen(navController = navController, id = id)
        }
        composable(route = "character/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            CharacterScreen(navController = navController, id = id)
        }
    }
}
