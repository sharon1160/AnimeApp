package com.example.animeapp.presentation.ui.search

import com.example.animeapp.domain.DetailedAnime
import com.example.animeapp.domain.Anime

data class SearchUiState(
    val query: String = "",
    val sortFilter: String = "Filter 1",
    val animes: List<Anime> = emptyList(),
    val selectedAnime: DetailedAnime? = null
)
