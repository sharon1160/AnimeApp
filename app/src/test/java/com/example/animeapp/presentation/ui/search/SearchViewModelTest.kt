package com.example.animeapp.presentation.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.example.animeapp.domain.enums.ContentType
import com.example.animeapp.domain.repository.AnimesRepository
import com.example.animeapp.domain.repository.FavoritesRepository
import com.example.animeapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private lateinit var animesRepository: AnimesRepository

    private lateinit var favoritesRepository: FavoritesRepository

    private lateinit var searchViewModel: SearchViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        favoritesRepository = mockk(relaxed = true)
        animesRepository = mockk(relaxed = true)
        searchViewModel = SearchViewModel(animesRepository, favoritesRepository)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchAnime should update the paginatedAnimes and uiState`() = runTest {
        val query = "query"
        val typeFilter = ContentType.ANIME

        val paginatedAnimes = PagingData.from(DomainModelMocks.animesListMock)
        val favorites = DomainModelMocks.animesListMock

        coEvery { animesRepository.getAnimes(query, typeFilter) } returns flowOf(paginatedAnimes)

        every { favoritesRepository.getAllFavorites() } returns flowOf(favorites)

        searchViewModel.searchAnime(query, typeFilter)

        val expectedUiState = SearchUiState(query = query)

        assertEquals(searchViewModel.uiState.value, expectedUiState)
        assertEquals(
            searchViewModel.paginatedAnimes.first()::class.java,
            paginatedAnimes::class.java
        )
    }
}
