package com.example.animeapp.presentation.ui.character

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.animeapp.R
import com.example.animeapp.presentation.ui.home.AnimeTopAppBar
import com.example.animeapp.presentation.ui.theme.AnimeAppTheme

@Composable
fun CharacterScreen(navController: NavHostController) {

    val navigateToFavorites = { navController.navigate("favorites") }
    val navigateToDetail = { navController.popBackStack() }

    CharacterContentScreen(navigateToFavorites, navigateToDetail)
}

@Composable
fun CharacterContentScreen(navigateToFavorites: () -> Unit, navigateToDetail: () -> Boolean) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(
                title = stringResource(R.string.character_screen_title),
                navigateToFavorites = navigateToFavorites,
                navigateToBack = navigateToDetail)
        },
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "character")
        }
    }
}

@Preview
@Composable
fun CharacterScreenPreview() {
    AnimeAppTheme {
        CharacterContentScreen({},{ false })
    }
}
