package com.example.animeapp.presentation.ui.character

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.animeapp.R
import com.example.animeapp.presentation.ui.home.AnimeTopAppBar
import com.example.animeapp.presentation.ui.theme.AnimeAppTheme
import com.example.animeapp.presentation.ui.theme.Roboto

@Composable
fun CharacterScreen(navController: NavHostController, image: String, name: String) {

    val navigateToFavorites = { navController.navigate("favorites") }
    val navigateToDetail = { navController.popBackStack() }

    CharacterContentScreen(
        navigateToFavorites,
        navigateToDetail,
        image,
        name
    )
}

@Composable
fun CharacterContentScreen(
    navigateToFavorites: () -> Unit,
    navigateToDetail: () -> Boolean,
    image: String,
    name: String
) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(
                title = stringResource(R.string.character_screen_title),
                navigateToFavorites = navigateToFavorites,
                navigateToBack = navigateToDetail
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 25.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Roboto,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 5.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .scale(Scale.FILL)
                        .build(),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(290.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun CharacterScreenPreview() {
    AnimeAppTheme {
        CharacterContentScreen({}, { false }, "image", "name")
    }
}
