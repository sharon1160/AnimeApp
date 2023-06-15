package com.example.animeapp.presentation.ui.navigation.graphs

import android.net.Uri
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
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt()
            DetailScreen(navController = navController, id = id)
        }
        composable(route = "character/{image}/{name}",
            arguments = listOf(
                navArgument("image") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val image =
                Uri.parse(Uri.decode(backStackEntry.arguments?.getString("image"))).toString()
            val name = backStackEntry.arguments?.getString("name").toString()
            CharacterScreen(navController = navController, image = image, name = name)
        }
    }
}
