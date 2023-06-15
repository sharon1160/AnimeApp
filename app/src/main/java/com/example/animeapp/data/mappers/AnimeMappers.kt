package com.example.animeapp.data

import com.example.AnimeQuery
import com.example.AnimesQuery
import com.example.animeapp.domain.Character
import com.example.animeapp.domain.DetailedAnime
import com.example.animeapp.domain.Anime

fun AnimeQuery.Media.toDomainModel(): DetailedAnime {
    return DetailedAnime(
        id = id,
        coverLargeImage = coverImage?.large ?: "",
        englishTitle = title?.english ?: "No title",
        japaneseTitle = title?.native ?: "No title",
        averageScore = averageScore ?: 0,
        type = type?.name ?: "",
        episodes = episodes ?: 0,
        genres = genres?.mapNotNull { it } ?: emptyList(),
        description = description ?: "No description",
        characters = characters?.edges?.mapNotNull {
            it?.let { edge ->
                edge.node?.let { node ->
                    Character(
                        id = node.id,
                        name = node.name?.full ?: "No name",
                        largeImage = node.image?.large ?: "",
                        mediumImage = node.image?.medium ?: ""
                    )
                }
            }
        } ?: emptyList()
    )
}

fun AnimesQuery.Medium.toDomainModel(): Anime {
    return Anime(
        id = id,
        coverLargeImage = coverImage?.large ?: "",
        englishTitle = title?.english ?: "No title",
        japaneseTitle = title?.native ?: "No title",
        type = type?.name ?: ""
    )
}
