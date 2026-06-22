package com.digiwizkid.islandhopper.ui.screens.home

import androidx.lifecycle.ViewModel
import com.digiwizkid.islandhopper.data.models.GameMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _selectedMode = MutableStateFlow<GameMode?>(null)
    val selectedMode: StateFlow<GameMode?> = _selectedMode.asStateFlow()

    val availableModes: List<ModeOption> = listOf(
        ModeOption(GameMode.SHAPES, "Shapes", "Learn about shapes!"),
        ModeOption(GameMode.LETTERS, "Letters", "Explore the alphabet!"),
        ModeOption(GameMode.NUMBERS, "Numbers", "Practice counting!")
    )

    fun selectMode(mode: GameMode) {
        _selectedMode.value = mode
    }
}

data class ModeOption(
    val mode: GameMode,
    val title: String,
    val description: String
)
