package com.example.animeapp.presentation.ui.favorites

import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.repository.FavoritesRepository
import com.example.animeapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    private lateinit var favoritesRepository: FavoritesRepository

    private lateinit var favoritesViewModel: FavoritesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        favoritesRepository = mockk(relaxed = true)
        favoritesViewModel = FavoritesViewModel(favoritesRepository)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when an anime is inserted`() = runTest {
        val anime = DomainModelMocks.animeMock
        val favoritesFlow = MutableStateFlow(listOf(anime))

        coEvery { favoritesRepository.getAllFavorites() } returns favoritesFlow.asStateFlow()

        coEvery { favoritesRepository.insert(anime) } returns Unit

        favoritesViewModel.insert(anime)

        val job = launch {
            favoritesViewModel.favoritesAnimes.collect { animeList ->
                assert(animeList.isNotEmpty())
                assertEquals(anime, favoritesViewModel.favoritesAnimes.value.last())
            }
        }
        job.cancel()
    }

    @Test
    fun `when an anime is deleted`() = runTest {
        val anime = DomainModelMocks.animeMock
        val favoritesFlow = MutableStateFlow(listOf(anime))

        coEvery { favoritesRepository.getAllFavorites() } returns favoritesFlow.asStateFlow()

        coEvery { favoritesRepository.delete(anime) } returns Unit

        favoritesViewModel.delete(anime)

        favoritesFlow.value = emptyList()

        val job = launch {
            favoritesViewModel.favoritesAnimes.collect { animeList ->
                assert(animeList.isEmpty())
                assertEquals(false, animeList.contains(anime))
            }
        }

        delay(500)
        job.cancel()
    }

    @Test
    fun `when favoritesRepository returns a favorites empty list as state flow`() = runTest {

        val favoritesFlow = MutableStateFlow(emptyList<Anime>())

        coEvery { favoritesRepository.getAllFavorites() } returns favoritesFlow.asStateFlow()

        assert(favoritesViewModel.favoritesAnimes.value.isEmpty())
    }
}
