package com.example.animeapp.presentation.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.animeapp.presentation.ui.theme.AnimeAppTheme
import com.example.animeapp.presentation.ui.theme.Roboto
import com.example.animeapp.R
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentType
import com.example.animeapp.presentation.ui.home.AnimeTopAppBar

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by searchViewModel.uiState.collectAsState()

    val navigateToDetail = { navController.navigate("detail") }
    val navigateToFavorites = { navController.navigate("favorites") }

    AnimeAppTheme {
        SearchScreenContent(
            uiState.query,
            searchViewModel::updateQuery,
            uiState.typeFilter,
            searchViewModel::getTypeFilters,
            searchViewModel::updateTypeFilter,
            uiState.animes,
            searchViewModel::searchAnime,
            {},
            {},
            navigateToDetail,
            navigateToFavorites
        )
    }
}

@Composable
fun SearchScreenContent(
    query: String,
    updateQuery: (String) -> Unit,
    typeFilter: ContentType,
    getTypeFilters: () -> List<ContentType>,
    updateTypeFilter: (String) -> Unit,
    animesList: List<Anime>?,
    searchAnime: (String, ContentType) -> Unit,
    insertFavorite: (Anime) -> Unit,
    deleteFavorite: (Anime) -> Unit,
    navigateToDetail: () -> Unit,
    navigateToFavorites: () -> Unit
) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(
                title = stringResource(R.string.search_screen_title),
                navigateToFavorites = navigateToFavorites,
                navigateToBack = {})
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SearchAnimeBar(searchAnime, query, typeFilter, updateQuery)
            Filters(query, searchAnime, typeFilter, getTypeFilters, updateTypeFilter)
            animesList?.let {
                if (animesList.isNotEmpty()) {
                    AnimesList(
                        animesList,
                        insertFavorite,
                        deleteFavorite,
                        navigateToDetail
                    )
                } else {
                    Message(stringResource(R.string.welcome_message))
                }
            }
            if (animesList == null) {
                Message(stringResource(R.string.welcome_message))
            }
        }
    }
}

@Composable
fun Filters(
    query: String,
    searchAnime: (String, ContentType) -> Unit,
    typeFilter: ContentType,
    getTypeFilters: () -> List<ContentType>,
    updateTypeFilter: (String) -> Unit,
) {
    val typeFiltersList = getTypeFilters()

    var selected = when(typeFilter) {
        ContentType.ANIME -> "Anime"
        ContentType.MANGA -> "Manga"
    }
    Row(
        modifier = Modifier
            .padding(top = 75.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        typeFiltersList.forEach {
            FilterChip(
                title = it.name.lowercase()
                    .replaceFirstChar { first -> first.uppercase() },
                selected = selected,
                onSelected = { filter ->
                    selected = filter
                    if (query.isNotEmpty()) {
                        searchAnime(query, it)
                    }
                    updateTypeFilter(selected)
                }
            )
        }
    }
}

@Composable
fun FilterChip(title: String, selected: String, onSelected: (String) -> Unit) {

    val isSelected = title == selected
    val background =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
    val contentColor = if (isSelected) Color.White else Color.Black

    Box(modifier = Modifier
        .padding(end = 10.dp)
        .height(35.dp)
        .clip(CircleShape)
        .background(background)
        .clickable(
            onClick = { onSelected(title) }
        )
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.check_icon_content_description),
                    tint = Color.White
                )
            }
            Text(
                text = title,
                color = contentColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = Roboto
            )
        }
    }
}

@Composable
fun AnimesList(
    animesList: List<Anime>,
    insertFavorite: (Anime) -> Unit,
    deleteFavorite: (Anime) -> Unit,
    navigateToDetail: () -> Unit
) {
    Box(modifier = Modifier.padding(top = 120.dp)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                start = 8.dp,
                top = 12.dp,
                end = 8.dp,
                bottom = 12.dp
            ),
            content = {
                items(animesList) { anime ->
                    AnimeCard(
                        anime,
                        insertFavorite,
                        deleteFavorite,
                        navigateToDetail
                    )
                }
            }
        )
    }
}

@Composable
fun AnimeCard(
    anime: Anime,
    insertFavorite: (Anime) -> Unit,
    deleteFavorite: (Anime) -> Unit,
    navigateToDetail: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(180.dp)
            .clickable {
                navigateToDetail()
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
                    .data(anime.coverLargeImage)
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
                        text = anime.japaneseTitle,
                        fontWeight = FontWeight.Light,
                        fontFamily = Roboto,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Box {
                    FavoritesButton(
                        anime,
                        insertFavorite,
                        deleteFavorite
                    )
                }
            }
        }
    }
}

@Composable
fun FavoritesButton(
    anime: Anime,
    insertFavorite: (Anime) -> Unit,
    deleteFavorite: (Anime) -> Unit
) {
    var isFavorite = anime.isFavorite

    IconButton(
        modifier = Modifier
            .size(30.dp),
        onClick = {
            if (!isFavorite) {
                insertFavorite(anime)
            } else {
                deleteFavorite(anime)
            }
            isFavorite = !isFavorite
        }
    ) {
        val icon = if (isFavorite) {
            Icons.Default.Favorite
        } else {
            Icons.Default.FavoriteBorder
        }
        val tint = if (isFavorite) Color.Red else Color.Gray
        Icon(
            imageVector = icon,
            contentDescription = stringResource(R.string.favorite_icon_content_description),
            tint = tint
        )
    }
}

@Composable
fun Message(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontFamily = Roboto,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAnimeBar(
    searchAnime: (String, ContentType) -> Unit,
    query: String,
    typeFilter: ContentType,
    updateQuery: (String) -> Unit
) {
    var active by remember { mutableStateOf(false) }

    Scaffold {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChange = {
                updateQuery(it)
            },
            onSearch = {
                if (it.isNotEmpty()) {
                    searchAnime(it, typeFilter)
                    active = false
                }
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_content_description)
                )
            },
            trailingIcon = {
                if (active) {
                    val emptyText = ""
                    Icon(
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) {
                                updateQuery(emptyText)
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_icon_content_description)
                    )
                }
            }
        ) {}
    }
}


@Preview
@Composable
fun SearchScreenPreview() {
    val list = listOf(
        Anime(
            1,
            "https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
            "English",
            "Japanese",
            "type"
        ),
        Anime(
            2,
            "https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
            "English",
            "Japanese",
            "type"
        ),
        Anime(
            3,
            "https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
            "English",
            "Japanese",
            "type"
        ),
        Anime(
            4,
            "https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
            "English",
            "Japanese",
            "type"
        ),
        Anime(
            5,
            "https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
            "English",
            "Japanese",
            "type"
        ),
        Anime(
            6,
            "https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
            "English",
            "Japanese",
            "type"
        )
    )
    AnimeAppTheme {
        SearchScreenContent(
            query = "",
            updateQuery = {},
            typeFilter = ContentType.ANIME,
            getTypeFilters = { emptyList() },
            updateTypeFilter = {},
            animesList = list,
            searchAnime = {_,_ ->},
            insertFavorite = {},
            deleteFavorite = {},
            navigateToDetail = {},
            navigateToFavorites = {}
        )
    }
}
