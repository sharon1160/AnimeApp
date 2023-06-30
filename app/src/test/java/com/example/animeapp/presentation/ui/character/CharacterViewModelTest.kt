package com.example.animeapp.presentation.ui.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animeapp.domain.usecase.GetCharacterUseCase
import com.example.animeapp.mocks.DomainConstantsMocks
import com.example.animeapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {
    @RelaxedMockK
    private lateinit var getCharacterUseCase: GetCharacterUseCase

    private lateinit var characterViewModel: CharacterViewModel

    private val detailedCharacterId = DomainConstantsMocks.CHARACTER_ID

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        characterViewModel = CharacterViewModel(getCharacterUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getCharacterUseCase returns a detailed character and set on ui state`() = runTest {
        // Given
        val detailedCharacter = DomainModelMocks.detailedCharacterMock
        coEvery { getCharacterUseCase.execute(any()) } returns detailedCharacter

        // When
        characterViewModel.searchCharacter(id = detailedCharacterId)

        // Then
        assertNotNull(characterViewModel.uiState.value.detailedCharacter)
        assert(characterViewModel.uiState.value.detailedCharacter == detailedCharacter)
    }

    @Test
    fun `when getCharacterUseCase returns a null and set on ui state`() = runTest {
        // Given
        coEvery { getCharacterUseCase.execute(any()) } returns null

        // When
        characterViewModel.searchCharacter(id = detailedCharacterId)

        // Then
        assertNull(characterViewModel.uiState.value.detailedCharacter)
    }
}
