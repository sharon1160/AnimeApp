package com.example.animeapp.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    val favoritesAnimes = favoritesRepository.getAllFavorites().flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun insert(anime: Anime) {
        viewModelScope.launch {
            favoritesRepository.insert(anime)
        }
    }

    fun delete(anime: Anime) {
        viewModelScope.launch {
            favoritesRepository.delete(anime)
        }
    }
}
