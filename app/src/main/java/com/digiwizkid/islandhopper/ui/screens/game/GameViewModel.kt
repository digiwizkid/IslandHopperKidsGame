package com.digiwizkid.islandhopper.ui.screens.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.digiwizkid.islandhopper.data.models.Difficulty
import com.digiwizkid.islandhopper.data.models.GameMode
import com.digiwizkid.islandhopper.data.models.GameUiState
import com.digiwizkid.islandhopper.data.models.Island
import com.digiwizkid.islandhopper.data.repository.GameRepository
import com.digiwizkid.islandhopper.data.repository.SoundManager
import com.digiwizkid.islandhopper.data.repository.TextToSpeechManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

        private val repository = GameRepository(application)
    internal val soundManager by lazy { SoundManager() }
    private val ttsManager = TextToSpeechManager(application)

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun startGame(mode: GameMode, timerMode: Boolean = false) {
        repository.loadQuestionsForMode(mode)
        _uiState.value = _uiState.value.copy(
            currentMode = mode,
            isTimerMode = timerMode,
            timeRemaining = 30,
            streak = 0,
            bestStreak = 0,
            difficulty = Difficulty.STARTER
        )
        loadQuestion()

        if (timerMode) {
            startTimer()
        }
    }

    fun toggleMusic() {
        val newState = !_uiState.value.isMusicOn
        _uiState.value = _uiState.value.copy(isMusicOn = newState)
        soundManager.setMusicOn(newState)
        ttsManager.setMusicOn(newState)
    }

    fun selectIsland(island: Island) {
        val currentState = _uiState.value
        if (currentState.isGameOver || !island.isEnabled) return

        val updatedIslands = currentState.activeIslands.map {
            if (it.id == island.id) it.copy(isEnabled = false) else it
        }
        _uiState.value = currentState.copy(activeIslands = updatedIslands)

        if (island.isCorrect) {
            val result = repository.recordAnswer(true)
            val newScore = repository.score.value
            val newStreak = result.streak
            val bestStreak = maxOf(_uiState.value.bestStreak, newStreak)

            soundManager.vibrate(getApplication())

            if (result.bonusAwarded) {
                soundManager.playStreakSound()
            } else {
                soundManager.playCorrectSound()
            }

            _uiState.value = _uiState.value.copy(
                score = newScore,
                streak = newStreak,
                bestStreak = bestStreak,
                showParticles = true,
                lastAnswerCorrect = true,
                difficulty = repository.getCurrentDifficulty()
            )

            viewModelScope.launch {
                delay(400)
                _uiState.value = _uiState.value.copy(showParticles = false)
            }

            if (result.difficultyAdvanced) {
                val newDiff = repository.getCurrentDifficulty()
                if (!result.bonusAwarded) {
                    soundManager.playStreakSound()
                }
                _uiState.value = _uiState.value.copy(
                    showLevelUp = true,
                    levelUpMessage = "Congratulations! You reached ${newDiff.displayName}!"
                )
                viewModelScope.launch {
                    delay(2000)
                    _uiState.value = _uiState.value.copy(showLevelUp = false)
                    if (repository.hasMoreQuestions()) {
                        _uiState.value = _uiState.value.copy(
                            characterPosition = island.id
                        )
                        loadQuestion()
                    } else {
                        _uiState.value = _uiState.value.copy(isGameOver = true)
                        soundManager.playGameOverSound()
                        timerJob?.cancel()
                    }
                }
            } else if (repository.hasMoreQuestions()) {
                repository.advanceToNextQuestion()
                _uiState.value = _uiState.value.copy(
                    characterPosition = island.id
                )
                loadQuestion()
            } else {
                _uiState.value = _uiState.value.copy(isGameOver = true)
                soundManager.playGameOverSound()
                timerJob?.cancel()
            }
        } else {
            repository.recordAnswer(false)
            soundManager.playWrongSound()
            soundManager.vibrate(getApplication())

            _uiState.value = _uiState.value.copy(
                triggerShakeId = island.id,
                streak = 0,
                lastAnswerCorrect = false
            )

            viewModelScope.launch {
                delay(600)
                _uiState.value = _uiState.value.copy(triggerShakeId = null)
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.timeRemaining > 0 && !_uiState.value.isGameOver) {
                delay(1000)
                val remaining = _uiState.value.timeRemaining - 1
                _uiState.value = _uiState.value.copy(timeRemaining = remaining)
                if (remaining <= 0) {
                    _uiState.value = _uiState.value.copy(isGameOver = true)
                    soundManager.playGameOverSound()
                }
            }
        }
    }

    private fun loadQuestion() {
        val question = repository.getCurrentQuestion()
        if (question != null) {
            _uiState.value = _uiState.value.copy(
                questionPrompt = question.first,
                activeIslands = question.second,
                currentQuestionIndex = repository.getCurrentQuestionIndex(),
                totalQuestions = repository.getTotalQuestions()
            )
            ttsManager.stop()
            ttsManager.speak(question.first)
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
        ttsManager.release()
        timerJob?.cancel()
    }
}
