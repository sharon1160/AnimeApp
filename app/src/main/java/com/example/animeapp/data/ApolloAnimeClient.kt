package com.example.animeapp.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.AnimeQuery
import com.example.AnimesQuery
import com.example.animeapp.data.mappers.toApolloModel
import com.example.animeapp.domain.AnimeClient
import com.example.animeapp.domain.DetailedAnime
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentSort
import com.example.animeapp.domain.enums.ContentType

class ApolloAnimeClient(
    private val apolloClient: ApolloClient
) : AnimeClient {
    override suspend fun getAnimes(
        page: Int,
        perPage: Int,
        search: String,
        sort: List<ContentSort>,
        type: ContentType
    ): List<Anime> {

        return apolloClient
            .query(
                AnimesQuery(
                    page = Optional.Present(page),
                    perPage = Optional.Present(perPage),
                    search = Optional.Present(search),
                    sort = Optional.Present(sort.map {
                        it.toApolloModel()
                    }),
                    type = Optional.Present(type.toApolloModel())
                )
            )
            .execute()
            .data
            ?.Page
            ?.media?.mapNotNull {
                it?.toDomainModel()
            } ?: emptyList()
    }

    override suspend fun getAnime(id: Int): DetailedAnime? {
        return apolloClient
            .query(AnimeQuery(Optional.Present(id)))
            .execute()
            .data
            ?.Media
            ?.toDomainModel()
    }
}
