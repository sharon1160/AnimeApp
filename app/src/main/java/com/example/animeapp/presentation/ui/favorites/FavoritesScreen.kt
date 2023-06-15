package com.example.animeapp.presentation.ui.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.ui.util.lerp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.animeapp.R
import com.example.animeapp.domain.Anime
import com.example.animeapp.presentation.ui.home.AnimeTopAppBar
import com.example.animeapp.presentation.ui.search.Message
import com.example.animeapp.presentation.ui.theme.AnimeAppTheme
import com.example.animeapp.presentation.ui.theme.Roboto
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val favoritesAnimes by favoritesViewModel.favoritesAnimes.collectAsState()

    val navigateToFavorites = { navController.navigate("favorites") }
    val navigateToBack = { navController.popBackStack() }
    val navigateToDetail = { id: Int -> navController.navigate("detail/$id")}

    AnimeAppTheme {
        FavoritesScreenContent(
            favoritesAnimes,
            favoritesViewModel::delete,
            navigateToFavorites,
            navigateToBack,
            navigateToDetail
        )
    }
}

@Composable
fun FavoritesScreenContent(
    favoritesList: List<Anime>,
    deleteFavorite: (Anime) -> Unit,
    navigateToFavorites: () -> Unit,
    navigateToBack: () -> Boolean,
    navigateToDetail: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(
                title = stringResource(R.string.favorites_screen_title),
                navigateToFavorites = navigateToFavorites,
                navigateToBack = navigateToBack
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(top = 20.dp)
        ) {
            CarouselCard(
                favoritesList,
                deleteFavorite,
                navigateToDetail
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselCard(
    favoritesList: List<Anime>,
    deleteFavorite: (Anime) -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    if (favoritesList.isNotEmpty()) {
        val pagerState = rememberPagerState(initialPage = 1)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                count = favoritesList.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 80.dp),
                modifier = Modifier
                    .height(750.dp)
            ) { page ->
                Card(
                    modifier = Modifier
                        .height(420.dp)
                        .width(250.dp)
                        .clickable {
                            navigateToDetail(favoritesList[page].id)
                        }
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            lerp(
                                start = 0.80f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                                .also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.Start
                    ) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(favoritesList[page].coverLargeImage)
                                .crossfade(true)
                                .scale(Scale.FILL)
                                .build(),
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .height(300.dp)
                                .width(280.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Box(modifier = Modifier.width(170.dp)) {
                                Text(
                                    text = favoritesList[page].japaneseTitle,
                                    fontWeight = FontWeight.Light,
                                    fontFamily = Roboto,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            DeleteButton(favoritesList[page], deleteFavorite)
                        }
                    }
                }
            }
        }
    } else {
        Message(stringResource(R.string.no_favorites_message))
    }
}

@Composable
fun DeleteButton(
    anime: Anime,
    deleteFavorite: (Anime) -> Unit
) {
    IconButton(
        modifier = Modifier.size(35.dp),
        onClick = {
            deleteFavorite(anime)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_content_description),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    AnimeAppTheme {
        FavoritesScreenContent(
            favoritesList = emptyList(),
            deleteFavorite = {},
            navigateToFavorites = {},
            navigateToBack = { false },
            navigateToDetail = {}
        )
    }
}
