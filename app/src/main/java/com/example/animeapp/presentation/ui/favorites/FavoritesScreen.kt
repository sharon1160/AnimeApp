package com.example.animeapp.presentation.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.animeapp.R
import com.example.animeapp.presentation.ui.home.AnimeTopAppBar
import com.example.animeapp.presentation.ui.theme.AnimeAppTheme

@Composable
fun FavoritesScreen(navController: NavHostController) {
    val navigateToFavorites = { navController.navigate("favorites") }
    val navigateToBack = { navController.navigate("search") }

    AnimeAppTheme {
        FavoritesScreenContent(navigateToFavorites, navigateToBack)
    }
}

@Composable
fun FavoritesScreenContent(navigateToFavorites: () -> Unit, navigateToBack: () -> Unit) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(
                title = stringResource(R.string.favorites_screen_title),
                navigateToFavorites = navigateToFavorites,
                navigateToBack = navigateToBack
            )
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            Text("Fav")
        }
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    AnimeAppTheme {
        FavoritesScreenContent(navigateToFavorites = {}, navigateToBack = {})
    }
}
