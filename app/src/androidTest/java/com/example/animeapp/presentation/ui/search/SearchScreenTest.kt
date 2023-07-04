package com.example.animeapp.presentation.ui.search

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentType
import com.example.animeapp.mocks.ConstantsMocks
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class SearchScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun searchQuery() {

        var navigateClick = false

        composeRule.setContent {
            SearchScreenContent(
                query = ConstantsMocks.EMPTY_STRING,
                updateQuery = {},
                typeFilter = ContentType.ANIME,
                getTypeFilters = { emptyList() },
                updateTypeFilter = {},
                resultedList = flowOf(PagingData.empty<Anime>()).collectAsLazyPagingItems(),
                searchAnime = { _, _ -> },
                insertFavorite = {},
                deleteFavorite = {},
                navigateToDetail = {},
                navigateToFavorites = {navigateClick = true}
            )
        }
        composeRule.onNodeWithContentDescription("Favorite Icon").performClick()
        assertTrue(navigateClick)
    }
}
