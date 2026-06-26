package com.digiwizkid.islandhopper.data.models

data class GameUiState(
    val score: Int = 0,
    val currentMode: GameMode? = null,
    val questionPrompt: String = "",
    val activeIslands: List<Island> = emptyList(),
    val characterPosition: Int = 0,
    val isGameOver: Boolean = false,
    val triggerShakeId: Int? = null,
    val streak: Int = 0,
    val bestStreak: Int = 0,
    val difficulty: Difficulty = Difficulty.STARTER,
    val isTimerMode: Boolean = false,
    val timeRemaining: Int = 30,
    val showParticles: Boolean = false,
    val lastAnswerCorrect: Boolean? = null,
    val isMusicOn: Boolean = true,
    val showLevelUp: Boolean = false,
    val levelUpMessage: String = "",
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = 0
)
