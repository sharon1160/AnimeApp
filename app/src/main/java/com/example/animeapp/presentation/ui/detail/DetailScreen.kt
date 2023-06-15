package com.example.animeapp.presentation.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.animeapp.domain.Character
import com.example.animeapp.domain.DetailedAnime
import com.example.animeapp.presentation.ui.home.AnimeTopAppBar
import com.example.animeapp.presentation.ui.theme.AnimeAppTheme
import com.example.animeapp.presentation.ui.theme.Roboto

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    navController: NavHostController, id: Int?
) {
    val uiState by detailViewModel.uiState.collectAsState()

    id?.let { detailViewModel.searchAnime(it) }

    val navigateToFavorites = { navController.navigate("favorites") }
    val navigateToSearch = { navController.popBackStack() }
    val navigateToCharacter = { characterId: Int ->
        navController.navigate("character/$characterId")
    }

    DetailScreenContent(
        navigateToFavorites,
        navigateToSearch,
        uiState.detailedAnime,
        navigateToCharacter
    )
}

@Composable
fun DetailScreenContent(
    navigateToFavorites: () -> Unit,
    navigateToSearch: () -> Boolean,
    detailedAnime: DetailedAnime?,
    navigateToCharacter: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(
                title = stringResource(R.string.detail_screen_title),
                navigateToFavorites = navigateToFavorites,
                navigateToBack = navigateToSearch
            )
        },
    ) { paddingValues ->
        detailedAnime?.let {
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
                        text = detailedAnime.japaneseTitle,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Roboto,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = detailedAnime.englishTitle,
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
                            .data(detailedAnime.coverLargeImage)
                            .scale(Scale.FILL)
                            .build(),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .width(200.dp)
                            .height(280.dp)
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
                AnimeDescription(detailedAnime)
                CharactersList(detailedAnime.characters, navigateToCharacter)
            }
        }
    }
}

@Composable
fun AnimeDescription(detailedAnime: DetailedAnime) {
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
                    stringResource(R.string.episodes_label),
                    detailedAnime.episodes.toString()
                )

                Spacer(modifier = Modifier.width(8.dp))

                PropertyField(
                    stringResource(R.string.score_label),
                    detailedAnime.averageScore.toString()
                )

                Spacer(modifier = Modifier.width(8.dp))

                var genres = ""
                detailedAnime.genres.map { genres += "$it, " }
                genres =
                    if (genres.isNotEmpty()) {
                        genres.dropLast(2)
                    } else {
                        stringResource(R.string.no_genres_label)
                    }
                PropertyField(stringResource(R.string.genres_label), genres)

                Spacer(modifier = Modifier.width(8.dp))

                PropertyField(stringResource(R.string.description_label), detailedAnime.description)

                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun PropertyField(label: String, content: String) {
    Row {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            fontFamily = Roboto,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = content,
            fontWeight = FontWeight.Light,
            fontFamily = Roboto,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun CharactersList(
    characters: List<Character>,
    navigateToCharacter: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 26.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = stringResource(R.string.characters_label),
            fontWeight = FontWeight.Bold,
            fontFamily = Roboto,
            style = MaterialTheme.typography.bodyMedium,
        )
        if (characters.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(
                    bottom = 12.dp
                ),
                content = {
                    items(characters.size) { index ->
                        CharacterCard(
                            characters[index],
                            navigateToCharacter
                        )
                    }
                }
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = stringResource(R.string.message_no_characters),
                    fontWeight = FontWeight.Light,
                    fontFamily = Roboto,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun CharacterCard(item: Character, navigateToCharacter: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(190.dp)
            .clickable {
                navigateToCharacter(item.id)
            },
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(9.dp),
            horizontalAlignment = Alignment.Start
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.largeImage)
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(modifier = Modifier.width(80.dp)) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Light,
                        fontFamily = Roboto,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun DetailScreenPreview() {
    AnimeAppTheme {
        DetailScreenContent({}, { false }, null, {})
    }
}
