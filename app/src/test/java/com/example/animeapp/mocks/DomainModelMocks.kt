package com.example.animeapp.mocks

import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.DetailedAnime
import com.example.animeapp.domain.DetailedCharacter

object DomainModelMocks {
    val detailedAnimeMock = DetailedAnime(
        id = 0,
        coverLargeImage = "coverLargeImage",
        englishTitle = "englishTitle",
        japaneseTitle = "japaneseTitle",
        type = "type",
        averageScore = 0,
        episodes = 0,
        genres = emptyList(),
        description = "description",
        characters = emptyList()
    )

    val animesListMock = listOf(
        Anime(
            id = 0,
            coverLargeImage = "coverLargeImage",
            englishTitle = "englishTitle",
            japaneseTitle = "japaneseTitle",
            type = "type",
            isFavorite = true
        )
    )

    val detailedCharacterMock = DetailedCharacter(
        id = 0,
        fullName = "fullName",
        nativeName = "nativeName",
        largeImage = "largeImage",
        gender = "gender",
        description = "description"
    )

    val animeMock = Anime(
        id = 0,
        coverLargeImage = "coverLargeImage",
        englishTitle = "englishTitle",
        japaneseTitle = "japaneseTitle",
        type = "type"
    )
}
