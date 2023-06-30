package com.example.animeapp.presentation.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animeapp.domain.usecase.GetAnimeUseCase
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
class DetailViewModelTest {
    @RelaxedMockK
    private lateinit var getAnimeUseCase: GetAnimeUseCase

    private lateinit var detailViewModel: DetailViewModel

    private val detailedAnimeId = DomainConstantsMocks.DETAILED_ANIME_ID

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        detailViewModel = DetailViewModel(getAnimeUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getAnimeUseCase returns a detailed anime and set on ui state`() = runTest {
        // Given
        val detailedAnime = DomainModelMocks.detailedAnimeMock
        coEvery { getAnimeUseCase.execute(any()) } returns detailedAnime

        // When
        detailViewModel.searchAnime(id = detailedAnimeId)

        // Then
        assertNotNull(detailViewModel.uiState.value.detailedAnime)
        assert(detailViewModel.uiState.value.detailedAnime == detailedAnime)
    }

    @Test
    fun `when getAnimeUseCase returns a null and set on ui state`() = runTest {
        // Given
        coEvery { getAnimeUseCase.execute(any()) } returns null

        // When
        detailViewModel.searchAnime(id = detailedAnimeId)

        // Then
        assertNull(detailViewModel.uiState.value.detailedAnime)
    }
}
