package com.example.animeapp.mocks

import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.Character
import com.example.animeapp.domain.DetailedAnime
import com.example.animeapp.domain.DetailedCharacter

object ModelMocks {
    val favoritesList = listOf(
        Anime(
            id = 1,
            coverLargeImage = "coverLargeImage",
            englishTitle = "englishTitle",
            japaneseTitle = "japaneseTitle",
            type = "type",
            isFavorite = true
        )
    )
    val detailedAnime = DetailedAnime(
        id = 1,
        coverLargeImage = "coverLargeImage",
        englishTitle = "englishTitle",
        japaneseTitle = "japaneseTitle",
        type = "type",
        averageScore = 1,
        episodes = 1,
        genres = listOf("genres"),
        description = "description",
        characters = listOf(
            Character(
                id = 1,
                name = "name",
                largeImage = "largeImage",
                mediumImage = "mediumImage",
            )
        )
    )
    val detailedCharacter = DetailedCharacter(
        id = 1,
        fullName = "fullName",
        nativeName = "nativeName",
        largeImage = "largeImage",
        gender = "gender",
        description = "description"
    )
}
