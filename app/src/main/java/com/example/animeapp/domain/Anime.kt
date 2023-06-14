package com.example.animeapp.domain

data class Anime(
    val id: Int,
    val coverLargeImage: String,
    val englishTitle: String,
    val japaneseTitle: String,
    val type: String,
    val isFavorite: Boolean = false
)
