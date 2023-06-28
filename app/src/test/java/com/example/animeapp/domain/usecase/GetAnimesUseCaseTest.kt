package com.example.animeapp.domain.usecase

import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.AnimeClient
import com.example.animeapp.mocks.DomainConstantsMocks
import com.example.animeapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class GetAnimesUseCaseTest {

    @RelaxedMockK
    lateinit var animeClient: AnimeClient

    lateinit var getAnimesUseCase: GetAnimesUseCase

    private val query = DomainConstantsMocks.QUERY

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getAnimesUseCase = GetAnimesUseCase(animeClient)
    }

    @Test
    fun `when the api returns empty list`() = runTest {
        // Given
        coEvery {
            animeClient.getAnimes(
                page = any(),
                perPage = any(),
                search = any(),
                sort = any(),
                type = any()
            )
        } returns emptyList()

        // When
        val response = getAnimesUseCase.execute(search = query)

        // Then
        coVerify(exactly = 1) {
            animeClient.getAnimes(
                page = any(),
                perPage = any(),
                search = any(),
                sort = any(),
                type = any()
            )
        }
        assertNotNull(response)
        assert(emptyList<Anime>() == response)
    }

    @Test
    fun `when the api returns a list`() = runTest {
        // Given
        val animesList = DomainModelMocks.animesListMock
        coEvery {
            animeClient.getAnimes(
                page = any(),
                perPage = any(),
                search = any(),
                sort = any(),
                type = any()
            )
        } returns animesList

        // When
        val response = getAnimesUseCase.execute(search = query)

        // Then
        coVerify(exactly = 1) {
            animeClient.getAnimes(
                page = any(),
                perPage = any(),
                search = any(),
                sort = any(),
                type = any()
            )
        }
        assertNotNull(response)
        assert(animesList::javaClass.name == response::javaClass.name)
        assert(animesList == response)
    }
}
