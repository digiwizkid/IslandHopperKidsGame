package com.digiwizkid.islandhopper.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digiwizkid.islandhopper.data.models.GameMode
import com.digiwizkid.islandhopper.data.models.GameUiState
import com.digiwizkid.islandhopper.data.models.Island
import com.digiwizkid.islandhopper.data.repository.GameRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun startGame(mode: GameMode) {
        repository.loadQuestionsForMode(mode)
        loadQuestion()
    }

    fun selectIsland(island: Island) {
        val currentState = _uiState.value

        if (!island.isEnabled) return

        val updatedIslands = currentState.activeIslands.map {
            if (it.id == island.id) it.copy(isEnabled = false) else it
        }
        _uiState.value = currentState.copy(activeIslands = updatedIslands)

        if (island.isCorrect) {
            repository.incrementScore()
            val newScore = repository.score.value

            if (repository.hasMoreQuestions()) {
                repository.advanceToNextQuestion()
                _uiState.value = _uiState.value.copy(
                    score = newScore,
                    characterPosition = island.id
                )
                loadQuestion()
            } else {
                _uiState.value = _uiState.value.copy(
                    score = newScore,
                    isGameOver = true
                )
            }
        } else {
            _uiState.value = _uiState.value.copy(triggerShakeId = island.id)
            viewModelScope.launch {
                delay(600)
                _uiState.value = _uiState.value.copy(triggerShakeId = null)
                val reEnabled = _uiState.value.activeIslands.map {
                    it.copy(isEnabled = false)
                }
                _uiState.value = _uiState.value.copy(activeIslands = reEnabled)
            }
        }
    }

    private fun loadQuestion() {
        val question = repository.getCurrentQuestion()
        if (question != null) {
            _uiState.value = _uiState.value.copy(
                questionPrompt = question.first,
                activeIslands = question.second
            )
        }
    }
}
