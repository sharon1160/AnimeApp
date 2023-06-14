package com.example.animeapp.domain

import com.example.animeapp.domain.enums.ContentSort
import com.example.animeapp.domain.enums.ContentType

interface AnimeClient {
    suspend fun getAnimes(
        page: Int,
        perPage: Int,
        search: String,
        sort: List<ContentSort>,
        type: ContentType
    ): List<Anime>

    suspend fun getAnime(id: Int): DetailedAnime?
}