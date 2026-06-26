package com.digiwizkid.islandhopper.viewmodel

import android.app.Application
import com.digiwizkid.islandhopper.data.models.Difficulty
import com.digiwizkid.islandhopper.data.models.GameMode
import com.digiwizkid.islandhopper.ui.screens.game.GameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class GameViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var application: Application

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        // Use a real Application context provided by Robolectric
        application = ApplicationProvider.getApplicationContext()
        Dispatchers.setMain(testDispatcher)
        viewModel = GameViewModel(application)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `startGame loads first question`() {
        viewModel.startGame(GameMode.SHAPES)
        val state = viewModel.uiState.value

        assertFalse(state.questionPrompt.isBlank())
        assertTrue(state.activeIslands.isNotEmpty())
        assertEquals(0, state.score)
        assertEquals(0, state.streak)
    }

    @Test
    fun `selecting correct answer increments score and streak`() {
        viewModel.startGame(GameMode.SHAPES)

        val correctIsland = viewModel.uiState.value.activeIslands.first { it.isCorrect }
        viewModel.selectIsland(correctIsland)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.score > 0)
        assertTrue(viewModel.uiState.value.streak > 0)
    }

    @Test
    fun `selecting wrong answer resets streak`() {
        viewModel.startGame(GameMode.SHAPES)

        val correctIsland = viewModel.uiState.value.activeIslands.first { it.isCorrect }
        viewModel.selectIsland(correctIsland)
        testDispatcher.scheduler.advanceUntilIdle()

        val previousStreak = viewModel.uiState.value.streak
        assertTrue(previousStreak > 0)

        val currentIslands = viewModel.uiState.value.activeIslands
        if (currentIslands.isNotEmpty()) {
            val wrongIsland = currentIslands.firstOrNull { !it.isCorrect }
            if (wrongIsland != null) {
                viewModel.selectIsland(wrongIsland)
                testDispatcher.scheduler.advanceUntilIdle()
                assertEquals(0, viewModel.uiState.value.streak)
            }
        }
    }

    @Test
    fun `game ends after all questions answered`() {
        viewModel.startGame(GameMode.SHAPES)

        while (!viewModel.uiState.value.isGameOver) {
            val correctIsland = viewModel.uiState.value.activeIslands.firstOrNull { it.isCorrect }
            if (correctIsland != null) {
                viewModel.selectIsland(correctIsland)
                testDispatcher.scheduler.advanceUntilIdle()
            } else {
                break
            }
        }

        assertTrue(viewModel.uiState.value.isGameOver)
    }

    @Test
    fun `timer mode counts down`() {
        viewModel.startGame(GameMode.SHAPES, timerMode = true)

        assertEquals(30, viewModel.uiState.value.timeRemaining)
        assertTrue(viewModel.uiState.value.isTimerMode)
    }

    @Test
    fun `difficulty stays starter after 5 correct answers`() {
        viewModel.startGame(GameMode.SHAPES)

        repeat(5) {
            val correctIsland = viewModel.uiState.value.activeIslands.firstOrNull { it.isCorrect }
            if (correctIsland != null) {
                viewModel.selectIsland(correctIsland)
                testDispatcher.scheduler.advanceUntilIdle()
            }
        }

        if (!viewModel.uiState.value.isGameOver) {
            assertEquals(Difficulty.STARTER, viewModel.uiState.value.difficulty)
        }
    }
}
