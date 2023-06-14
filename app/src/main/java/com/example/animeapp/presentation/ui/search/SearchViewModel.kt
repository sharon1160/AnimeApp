package com.example.animeapp.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentType
import com.example.animeapp.domain.repository.AnimesRepository
import com.example.animeapp.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val animesRepository: AnimesRepository,
    favoriteRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _paginatedAnimes = MutableStateFlow<PagingData<Anime>>(PagingData.empty())
    private val paginatedAnimes = _paginatedAnimes.cachedIn(viewModelScope)

    private val favoritesAnimes =  favoriteRepository.getAllFavorites().flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val resultedList = combine(paginatedAnimes,favoritesAnimes) { paginatedAnimes, favoritesAnimes ->
        paginatedAnimes.map { anime ->
            if (favoritesAnimes.contains(anime)) {
                anime.copy(isFavorite = true)
            } else {
                anime
            }
        }
    }

    fun searchAnime(query: String, typeFilter: ContentType) {
        animesRepository.getAnimes(query, typeFilter).onEach { paginatedAnimes ->
                _paginatedAnimes.update {
                    paginatedAnimes
                }
            }.launchIn(viewModelScope)
            _uiState.update {
                it.copy(query = query)
            }
    }

    fun getTypeFilters(): List<ContentType> {
        return enumValues<ContentType>().toList()
    }

    fun updateQuery(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun updateTypeFilter(type: String) {
        val typeFilter = when (type) {
            "Anime" -> ContentType.ANIME
            "Manga" -> ContentType.MANGA
            else -> {
                ContentType.ANIME
            }
        }
        _uiState.update {
            it.copy(typeFilter = typeFilter)
        }
    }
}
