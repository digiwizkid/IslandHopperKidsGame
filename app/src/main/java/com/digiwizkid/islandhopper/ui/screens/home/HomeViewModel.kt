package com.digiwizkid.islandhopper.ui.screens.home

import androidx.lifecycle.ViewModel
import com.digiwizkid.islandhopper.data.models.GameMode

class HomeViewModel : ViewModel() {

    val availableModes: List<ModeOption> = listOf(
        ModeOption(GameMode.SHAPES, "Shapes", "Learn about shapes!"),
        ModeOption(GameMode.LETTERS, "Letters", "Explore the alphabet!"),
        ModeOption(GameMode.NUMBERS, "Numbers", "Practice counting!")
    )
}

data class ModeOption(
    val mode: GameMode,
    val title: String,
    val description: String
)
