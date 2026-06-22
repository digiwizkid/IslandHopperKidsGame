package com.digiwizkid.islandhopper.viewmodel

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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GameViewModel()
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
    }

    @Test
    fun `selecting correct answer increments score`() {
        viewModel.startGame(GameMode.SHAPES)

        val correctIsland = viewModel.uiState.value.activeIslands.first { it.isCorrect }
        viewModel.selectIsland(correctIsland)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.score > 0)
    }

    @Test
    fun `selecting wrong answer does not increment score`() {
        viewModel.startGame(GameMode.SHAPES)

        val wrongIsland = viewModel.uiState.value.activeIslands.first { !it.isCorrect }
        viewModel.selectIsland(wrongIsland)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(0, viewModel.uiState.value.score)
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
}
