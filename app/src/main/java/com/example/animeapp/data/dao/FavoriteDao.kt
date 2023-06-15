package com.example.animeapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animeapp.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteMovie: Favorite)

    @Query("DELETE FROM favorites WHERE japaneseTitle = :japaneseTitle")
    suspend fun delete(japaneseTitle: String)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorites()
}
