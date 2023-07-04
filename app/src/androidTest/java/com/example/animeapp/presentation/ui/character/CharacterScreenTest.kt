package com.example.animeapp.presentation.ui.character

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

class CharacterScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun verifyNavigationToFavorites() {
        var navigateClick = false

        composeRule.setContent {
            CharacterContentScreen(
                navigateToFavorites = { navigateClick = true },
                navigateToDetail = { true },
                detailedCharacter = ModelMocks.detailedCharacter
            )
        }
        composeRule.onNodeWithContentDescription("Favorite Icon").performClick()
        assertTrue(navigateClick)
    }

    @Test
    fun verifyNavigationToBack() {
        var navigateClick = false

        composeRule.setContent {
            CharacterContentScreen(
                navigateToFavorites = { },
                navigateToDetail = {
                    navigateClick = true
                    true
                },
                detailedCharacter = ModelMocks.detailedCharacter
            )
        }
        composeRule.onNodeWithContentDescription("Back icon").performClick()
        assertTrue(navigateClick)
    }

    @Test
    fun verifyCharacterDataFields() {
        composeRule.setContent {
            CharacterContentScreen(
                navigateToFavorites = { },
                navigateToDetail = { true },
                detailedCharacter = ModelMocks.detailedCharacter
            )
        }
        composeRule.onNodeWithTag(TestTags.FULL_NAME_TEXT).assertExists()
        composeRule.onNodeWithTag(TestTags.NATIVE_NAME_TEXT).assertExists()
        composeRule.onAllNodesWithTag(TestTags.LABEL_TEXT).assertCountEquals(2)
    }

    @Test
    fun verifyCharacterImage() {
        composeRule.setContent {
            CharacterContentScreen(
                navigateToFavorites = { },
                navigateToDetail = { true },
                detailedCharacter = ModelMocks.detailedCharacter
            )
        }
        composeRule.onNodeWithContentDescription("Character image").assertExists()
    }
}
