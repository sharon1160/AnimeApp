package com.example.animeapp.domain.repository

import com.example.animeapp.data.dao.FavoriteDao
import com.example.animeapp.data.entity.Favorite
import com.example.animeapp.domain.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoritesRepository {

    override suspend fun insert(anime: Anime) {
        val entity = Favorite(
            idFavorite = anime.id,
            coverLargeImage = anime.coverLargeImage,
            englishTitle = anime.englishTitle,
            japaneseTitle = anime.japaneseTitle,
            type = anime.type,
            isFavorite = anime.isFavorite
        )
        favoriteDao.insert(entity)
    }

    override suspend fun delete(anime: Anime) {
        favoriteDao.delete(anime.japaneseTitle)
    }

    override fun getAllFavorites(): Flow<List<Anime>> {
        val entities = favoriteDao.getAllFavorites()
        return entities.map { list ->
            list.map { favorite ->
                Anime(
                    id = favorite.idFavorite,
                    coverLargeImage = favorite.coverLargeImage,
                    englishTitle = favorite.englishTitle,
                    japaneseTitle = favorite.japaneseTitle,
                    type = favorite.type,
                    isFavorite = favorite.isFavorite
                )
            }
        }
    }
}
