package com.example.animeapp.domain.usecase

import com.example.animeapp.domain.AnimeClient
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentSort
import com.example.animeapp.domain.enums.ContentType

class GetAnimesUseCase(
    private val animeClient: AnimeClient
) {
    suspend fun execute(
        page: Int = DEFAULT_PAGE,
        perPage: Int = DEFAULT_PER_PAGE,
        search: String,
        sort: List<ContentSort> = DEFAULT_SORT,
        type: ContentType = DEFAULT_TYPE
    ): List<Anime> {
        return animeClient
            .getAnimes(page, perPage, search, sort, type)
    }

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PER_PAGE = 10
        val DEFAULT_TYPE = ContentType.ANIME
        val DEFAULT_SORT = listOf(ContentSort.TITLE_ENGLISH)
    }
}
