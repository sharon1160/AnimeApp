package com.example.animeapp.domain.usecase

import com.example.animeapp.domain.AnimeClient
import com.example.animeapp.domain.DetailedCharacter

class GetCharacterUseCase(
    private val animeClient: AnimeClient
) {
    suspend fun execute(id: Int): DetailedCharacter? {
        return animeClient
            .getCharacter(id)
    }
}
