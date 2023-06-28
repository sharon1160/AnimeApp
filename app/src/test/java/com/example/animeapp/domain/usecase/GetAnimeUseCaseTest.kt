package com.example.animeapp.domain.usecase

import com.example.animeapp.domain.AnimeClient
import com.example.animeapp.mocks.DomainConstantsMocks
import com.example.animeapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test


class GetAnimeUseCaseTest {

    @RelaxedMockK
    lateinit var animeClient: AnimeClient

    lateinit var getAnimeUseCase: GetAnimeUseCase

    private val detailedAnimeId = DomainConstantsMocks.DETAILED_ANIME_ID

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getAnimeUseCase = GetAnimeUseCase(animeClient)
    }

    @Test
    fun `when the api returns null`() = runTest {
        // Given
        coEvery { animeClient.getAnime(id = any()) } returns null

        // When
        val response = getAnimeUseCase.execute(id = detailedAnimeId)

        // Then
        coVerify(exactly = 1) { animeClient.getAnime(id = detailedAnimeId) }
        assertNull(response)
    }

    @Test
    fun `when the api returns something`() = runTest {
        // Given
        val detailedAnime = DomainModelMocks.detailedAnimeMock
        coEvery { animeClient.getAnime(any()) } returns detailedAnime

        // When
        val response = getAnimeUseCase.execute(id = detailedAnimeId)

        // Then
        coVerify(exactly = 1) { animeClient.getAnime(id = detailedAnimeId) }
        assertNotNull(response)
        response?.let {
            assert(detailedAnime::javaClass.name == response::javaClass.name)
            assert(detailedAnime.id == response.id)
            assert(detailedAnime.genres == response.genres)
            assert(detailedAnime.characters == response.characters)
        }
    }
}
