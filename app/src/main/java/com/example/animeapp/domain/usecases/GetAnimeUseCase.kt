package com.example.animeapp.domain.usecases

import com.example.animeapp.domain.AnimeClient
import com.example.animeapp.domain.DetailedAnime

class GetAnimeUseCase(
    private val animeClient: AnimeClient
) {
    suspend fun execute(id: Int): DetailedAnime? {
        return animeClient
            .getAnime(id)
    }
}