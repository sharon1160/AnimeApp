package com.example.animeapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "idFavorite") val idFavorite: Int,
    @ColumnInfo(name = "coverLargeImage") val coverLargeImage: String,
    @ColumnInfo(name = "englishTitle") val englishTitle: String,
    @ColumnInfo(name = "japaneseTitle") val japaneseTitle: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
)
