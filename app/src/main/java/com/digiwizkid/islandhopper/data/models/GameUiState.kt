package com.digiwizkid.islandhopper.data.models

data class GameUiState(
    val score: Int = 0,
    val currentMode: GameMode? = null,
    val questionPrompt: String = "",
    val activeIslands: List<Island> = emptyList(),
    val characterPosition: Int = 0,
    val isGameOver: Boolean = false,
    val triggerShakeId: Int? = null
)
