package com.example.animeapp.domain

data class DetailedAnime(
    val id: Int,
    val coverLargeImage: String,
    val englishTitle: String,
    val japaneseTitle: String,
    val type: String,
    val averageScore: Int,
    val episodes: Int,
    val genres: List<String>,
    val description: String,
    val characters: List<Character>,
)

data class Character(
    val id: Int,
    val name: String,
    val largeImage: String,
    val mediumImage: String
)
