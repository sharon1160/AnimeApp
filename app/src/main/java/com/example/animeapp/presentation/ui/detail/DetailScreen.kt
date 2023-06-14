package com.example.animeapp.presentation.ui.detail

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
fun DetailScreen(navController: NavHostController) {

    val navigateToFavorites = { navController.navigate("favorites") }
    val navigateToSearch = { navController.navigate("search") }

    DetailScreenContent(navigateToFavorites, navigateToSearch)
}

@Composable
fun DetailScreenContent(navigateToFavorites: () -> Unit, navigateToSearch: () -> Unit) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(
                title = stringResource(R.string.detail_screen_title),
                navigateToFavorites = navigateToFavorites,
                navigateToBack = navigateToSearch)
        },
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Text("detail")
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    AnimeAppTheme {
        DetailScreenContent({},{})
    }
}
