package com.example.animeapp.presentation.ui.fakeRepositories

import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeFavoritesRepository : FavoritesRepository {

    private val favoritesList: MutableStateFlow<List<Anime>> = MutableStateFlow(emptyList())

    override suspend fun insert(anime: Anime) {
        val currentList = favoritesList.value.toMutableList()
        currentList.add(anime)
        favoritesList.value = currentList
    }

    override suspend fun delete(anime: Anime) {
        val currentList = favoritesList.value.toMutableList()
        currentList.remove(anime)
        favoritesList.value = currentList
    }

    override fun getAllFavorites(): Flow<List<Anime>> {
        return favoritesList.asStateFlow()
    }
}
