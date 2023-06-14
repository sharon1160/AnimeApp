package com.example.animeapp.presentation.ui.home

import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.animeapp.presentation.ui.navigation.graphs.HomeNavGraph
import com.example.animeapp.R

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    HomeScreenContent(navController)
}

@Composable
fun HomeScreenContent(navController: NavHostController) {
    HomeNavGraph(navController = navController)
}

@Composable
fun AnimeTopAppBar(title: String, navigateToFavorites: () -> Unit, navigateToBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = title,
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.titleLarge
        ) },
        navigationIcon = {
            IconButton(onClick = { navigateToBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_icon_content_description),
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        },
        actions = {
            IconButton(onClick = { navigateToFavorites() }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(R.string.favorite_icon_navigation_content_description),
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.primary
    )
}
