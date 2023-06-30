package com.example.animeapp.domain.repository

import com.example.animeapp.domain.Anime
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun insert(anime: Anime)

    suspend fun delete(anime: Anime)

    fun getAllFavorites(): Flow<List<Anime>>
}
