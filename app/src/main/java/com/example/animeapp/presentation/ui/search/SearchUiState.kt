package com.example.animeapp.presentation.ui.search

import com.example.animeapp.domain.DetailedAnime
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentType

data class SearchUiState(
    val query: String = "",
    val typeFilter: ContentType = ContentType.ANIME,
    val animes: List<Anime> = emptyList(),
    val selectedAnime: DetailedAnime? = null
)
