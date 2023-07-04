package com.example.animeapp.presentation.ui.favorites

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.animeapp.core.util.TestTags
import com.example.animeapp.mocks.ModelMocks
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class FavoritesScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun verifyNavigationToFavorites() {
        var navigateClick = false
        composeRule.setContent {
            FavoritesScreenContent(
                favoritesList = ModelMocks.favoritesList,
                deleteFavorite = {},
                navigateToFavorites = { navigateClick = true },
                navigateToBack = { true },
                navigateToDetail = {}
            )
        }
        composeRule.onNodeWithContentDescription("Favorite Icon").performClick()
        assertTrue(navigateClick)
    }

    @Test
    fun verifyNavigationToBack() {
        var navigateClick = false
        composeRule.setContent {
            FavoritesScreenContent(
                favoritesList = ModelMocks.favoritesList,
                deleteFavorite = {},
                navigateToFavorites = { },
                navigateToBack = {
                    navigateClick = true
                    true
                },
                navigateToDetail = {}
            )
        }
        composeRule.onNodeWithContentDescription("Back icon").performClick()
        assertTrue(navigateClick)
    }

    @Test
    fun verifyCarouselCard() {
        composeRule.setContent {
            FavoritesScreenContent(
                favoritesList = ModelMocks.favoritesList,
                deleteFavorite = {},
                navigateToFavorites = { },
                navigateToBack = { true },
                navigateToDetail = {}
            )
        }
        composeRule.onNodeWithTag(TestTags.CAROUSEL_CARD).assertExists()
        composeRule.onAllNodesWithTag(TestTags.MESSAGE_NO_FAVORITES).assertCountEquals(0)
    }
}
