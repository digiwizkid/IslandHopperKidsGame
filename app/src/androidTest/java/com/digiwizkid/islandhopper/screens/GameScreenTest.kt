package com.digiwizkid.islandhopper.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.digiwizkid.islandhopper.data.models.GameMode
import com.digiwizkid.islandhopper.ui.screens.game.GameScreen
import org.junit.Rule
import org.junit.Test

class GameScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun gameScreen_displaysQuestion() {
        composeTestRule.setContent {
            GameScreen(
                mode = GameMode.SHAPES,
                onGameOver = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Which shape has three sides?")
            .assertExists()
    }
}
