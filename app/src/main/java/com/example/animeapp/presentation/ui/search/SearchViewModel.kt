package com.example.animeapp.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.enums.ContentType
import com.example.animeapp.domain.usecase.GetAnimesUseCase
import com.example.animeapp.domain.usecase.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAnimesUseCase: GetAnimesUseCase,
    private val getAnimeUseCase: GetAnimeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun searchAnime(query: String, typeFilter: ContentType) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    animes = getAnimesUseCase
                        .execute(
                            search = query,
                            type = typeFilter
                        )
                )
            }
            _uiState.update {
                it.copy(query = query)
            }
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
