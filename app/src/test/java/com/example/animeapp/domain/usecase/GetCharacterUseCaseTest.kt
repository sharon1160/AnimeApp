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

class GetCharacterUseCaseTest {

    @RelaxedMockK
    lateinit var animeClient: AnimeClient

    lateinit var getCharacterUseCase: GetCharacterUseCase

    private val detailedCharacterId = DomainConstantsMocks.CHARACTER_ID

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCharacterUseCase = GetCharacterUseCase(animeClient)
    }

    @Test
    fun `when the api returns null`() = runTest {
        // Given
        coEvery { animeClient.getCharacter(id = any()) } returns null

        // When
        val response = getCharacterUseCase.execute(id = detailedCharacterId)

        // Then
        coVerify(exactly = 1) { animeClient.getCharacter(id = detailedCharacterId) }
        assertNull(response)
    }

    @Test
    fun `when the api returns something`() = runTest {
        // Given
        val detailedCharacter = DomainModelMocks.detailedCharacterMock
        coEvery { animeClient.getCharacter(any()) } returns detailedCharacter

        // When
        val response = getCharacterUseCase.execute(id = detailedCharacterId)

        // Then
        coVerify(exactly = 1) { animeClient.getCharacter(id = detailedCharacterId) }
        assertNotNull(response)
        response?.let {
            assert(detailedCharacter::javaClass.name == response::javaClass.name)
            assert(detailedCharacter.id == response.id)
        }
    }
}
