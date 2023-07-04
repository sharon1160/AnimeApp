package com.example.animeapp.presentation.ui.detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.animeapp.core.util.TestTags
import com.example.animeapp.mocks.ModelMocks
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun verifyNavigationToFavorites() {
        var navigateClick = false

        composeRule.setContent {
            DetailScreenContent(
                navigateToFavorites = { navigateClick = true },
                navigateToSearch = { true },
                detailedAnime = ModelMocks.detailedAnime,
                navigateToCharacter = {}
            )
        }
        composeRule.onNodeWithContentDescription("Favorite Icon").performClick()
        assertTrue(navigateClick)
    }

    @Test
    fun verifyNavigationToBack() {
        var navigateClick = false

        composeRule.setContent {
            DetailScreenContent(
                navigateToFavorites = {},
                navigateToSearch = {
                    navigateClick = true
                    true
                },
                detailedAnime = ModelMocks.detailedAnime,
                navigateToCharacter = {}
            )
        }

        composeRule.onNodeWithContentDescription("Back icon").performClick()
        assertTrue(navigateClick)
    }

    @Test
    fun verifyAnimeDetails() {
        composeRule.setContent {
            DetailScreenContent(
                navigateToFavorites = {},
                navigateToSearch = { true },
                detailedAnime = ModelMocks.detailedAnime,
                navigateToCharacter = {}
            )
        }
        composeRule.onNodeWithText(ModelMocks.detailedAnime.englishTitle).assertExists()
        composeRule.onNodeWithText(ModelMocks.detailedAnime.japaneseTitle).assertExists()
        composeRule.onNodeWithTag(TestTags.ANIME_DESCRIPTION).assertExists()
        composeRule.onNodeWithContentDescription("Image detail").assertExists()
    }

    @Test
    fun verifyCharactersList() {
        composeRule.setContent {
            DetailScreenContent(
                navigateToFavorites = {},
                navigateToSearch = { true },
                detailedAnime = ModelMocks.detailedAnime,
                navigateToCharacter = {}
            )
        }
        composeRule.onNodeWithTag(TestTags.CHARACTERS_LIST).assertExists()
    }
}
