package com.digiwizkid.islandhopper.data.models

data class Island(
    val id: Int,
    val label: String,
    val isCorrect: Boolean,
    val isEnabled: Boolean = true
)
