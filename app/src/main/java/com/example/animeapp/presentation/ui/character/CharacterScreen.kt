package com.example.animeapp.presentation.ui.character

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.animeapp.R
import com.example.animeapp.domain.DetailedCharacter
import com.example.animeapp.presentation.ui.detail.PropertyField
import com.example.animeapp.presentation.ui.home.AnimeTopAppBar
import com.example.animeapp.presentation.ui.theme.AnimeAppTheme
import com.example.animeapp.presentation.ui.theme.Roboto

@Composable
fun CharacterScreen(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    navController: NavHostController,
    id: Int?
) {
    val uiState by characterViewModel.uiState.collectAsState()

    id?.let { characterViewModel.searchCharacter(it) }

    val navigateToFavorites = { navController.navigate("favorites") }
    val navigateToDetail = { navController.popBackStack() }

    CharacterContentScreen(
        navigateToFavorites,
        navigateToDetail,
        uiState.detailedCharacter
    )
}

@Composable
fun CharacterContentScreen(
    navigateToFavorites: () -> Unit,
    navigateToDetail: () -> Boolean,
    detailedCharacter: DetailedCharacter?,
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
        detailedCharacter?.let {
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
                    androidx.compose.material3.Text(
                        text = detailedCharacter.fullName,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Roboto,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    androidx.compose.material3.Text(
                        text = detailedCharacter.nativeName,
                        fontWeight = FontWeight.Light,
                        fontFamily = Roboto,
                        style = MaterialTheme.typography.titleMedium,
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
                            .data(detailedCharacter.largeImage)
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
                CharacterDescription(detailedCharacter)
            }
        }
    }
}

@Composable
fun CharacterDescription(detailedCharacter: DetailedCharacter) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Card(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                PropertyField(
                    stringResource(R.string.gender_label),
                    detailedCharacter.gender
                )
                Spacer(modifier = Modifier.width(8.dp))
                PropertyField(
                    stringResource(R.string.description_label),
                    detailedCharacter.description
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun CharacterScreenPreview() {
    val character = DetailedCharacter(
        1,
        "fullName",
        "nativeName",
        "image",
        "gender",
        "description"
    )
    AnimeAppTheme {
        CharacterContentScreen({}, { false }, character)
    }
}
