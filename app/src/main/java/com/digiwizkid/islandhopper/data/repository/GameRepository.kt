package com.digiwizkid.islandhopper.data.repository

import com.digiwizkid.islandhopper.data.models.GameMode
import com.digiwizkid.islandhopper.data.models.Island
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameRepository {

    private val questions = mapOf(
        GameMode.SHAPES to listOf(
            "Which one is a circle?" to listOf(
                Island(1, "Circle", true),
                Island(2, "Square", false),
                Island(3, "Triangle", false)
            ),
            "Which one has 4 equal sides?" to listOf(
                Island(1, "Rectangle", false),
                Island(2, "Square", true),
                Island(3, "Circle", false)
            )
        ),
        GameMode.LETTERS to listOf(
            "Which letter starts the word 'Apple'?" to listOf(
                Island(1, "A", true),
                Island(2, "B", false),
                Island(3, "C", false)
            ),
            "Which letter comes after 'D'?" to listOf(
                Island(1, "E", true),
                Island(2, "F", false),
                Island(3, "G", false)
            )
        ),
        GameMode.NUMBERS to listOf(
            "Which is the largest number?" to listOf(
                Island(1, "3", false),
                Island(2, "7", true),
                Island(3, "1", false)
            ),
            "What is 2 + 3?" to listOf(
                Island(1, "4", false),
                Island(2, "5", true),
                Island(3, "6", false)
            )
        )
    )

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private var currentQuestionIndex = 0
    private var currentModeQuestions = emptyList<Pair<String, List<Island>>>()

    fun loadQuestionsForMode(mode: GameMode) {
        currentModeQuestions = questions[mode] ?: emptyList()
        currentQuestionIndex = 0
        _score.value = 0
    }

    fun getCurrentQuestion(): Pair<String, List<Island>>? {
        return currentModeQuestions.getOrNull(currentQuestionIndex)
    }

    fun hasMoreQuestions(): Boolean {
        return currentQuestionIndex < currentModeQuestions.lastIndex
    }

    fun advanceToNextQuestion() {
        if (hasMoreQuestions()) {
            currentQuestionIndex++
        }
    }

    fun incrementScore() {
        _score.value = _score.value + 1
    }

    fun reset() {
        currentQuestionIndex = 0
        currentModeQuestions = emptyList()
        _score.value = 0
    }
}
