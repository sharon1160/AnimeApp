package com.example.animeapp.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.usecases.GetAnimesUseCase
import com.example.animeapp.domain.usecases.GetAnimeUseCase
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
): ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun searchAnime(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(animes = getAnimesUseCase.execute(search = query))
            }
        }
    }

    fun updateQuery(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun updateSortFilter(sortFilter: String) {
        _uiState.update {
            it.copy(sortFilter = sortFilter)
        }
    }

    fun searchAnime(query: String, filter: String = DEFAULT_FILTER) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    companion object {
        const val DEFAULT_FILTER = "Filter 1"
    }
}
