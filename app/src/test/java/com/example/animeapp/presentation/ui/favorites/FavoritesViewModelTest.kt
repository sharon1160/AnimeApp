package com.example.animeapp.presentation.ui.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animeapp.domain.Anime
import com.example.animeapp.mocks.DomainModelMocks
import com.example.animeapp.presentation.ui.fakeRepositories.FakeFavoritesRepository
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when an anime is inserted`() = runTest {
        val fakeFavoritesRepository = FakeFavoritesRepository()
        val favoritesViewModel = FavoritesViewModel(fakeFavoritesRepository)

        val anime = DomainModelMocks.animeMock
        withContext(Dispatchers.IO) {
            favoritesViewModel.insert(anime)
        }

        assert(favoritesViewModel.favoritesAnimes.value.isNotEmpty())
        assertEquals(anime, favoritesViewModel.favoritesAnimes.value.last())
    }

    @Test
    fun `when an anime is inserted and deleted`() = runTest {
        val fakeFavoritesRepository = FakeFavoritesRepository()
        val favoritesViewModel = FavoritesViewModel(fakeFavoritesRepository)
        val anime = DomainModelMocks.animeMock

        withContext(Dispatchers.IO) {
            favoritesViewModel.insert(anime)
        }
        assert(favoritesViewModel.favoritesAnimes.value.isNotEmpty())
        assertEquals(anime, favoritesViewModel.favoritesAnimes.value.last())

        withContext(Dispatchers.IO) {
            favoritesViewModel.delete(anime)
        }
        assertEquals(false, favoritesViewModel.favoritesAnimes.value.contains(anime))
    }

    @Test
    fun `when favoritesRepository returns a favorites list as state flow`() = runTest {
        val fakeFavoritesRepository = FakeFavoritesRepository()
        val favoritesViewModel = FavoritesViewModel(fakeFavoritesRepository)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favoritesViewModel.favoritesAnimes.collect()
        }
        assertEquals(emptyList<Anime>(), favoritesViewModel.favoritesAnimes.value)
    }
}
