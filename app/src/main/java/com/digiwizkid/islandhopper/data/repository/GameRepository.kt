package com.digiwizkid.islandhopper.data.repository

import android.content.Context
import com.digiwizkid.islandhopper.data.models.Difficulty
import com.digiwizkid.islandhopper.data.models.GameMode
import com.digiwizkid.islandhopper.data.models.Island
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONException
import org.json.JSONObject

// Type alias for a question and its associated islands
typealias Question = Pair<String, List<Island>>

/**
 * Repository that provides game questions. The questions are stored in a JSON asset
 * located at `app/src/main/assets/questions.json`. This allows the data to be
 * edited without recompiling the source.
 */

internal class GameRepository(private val context: Context) {

    // Load the questions once at construction time
    private val questionsByDifficulty: Map<Difficulty, Map<GameMode, List<Question>>>
        = loadQuestionsFromJson()

    /**
     * Reads the JSON asset and builds the nested map used by the game logic.
     * The JSON structure mirrors the original hard‑coded map.
     */
    private fun loadQuestionsFromJson(): Map<Difficulty, Map<GameMode, List<Question>>> {
        return try {
            val jsonString = context.assets.open("questions.json").bufferedReader().use { it.readText() }
            val rootObj = JSONObject(jsonString)
            val result = mutableMapOf<Difficulty, MutableMap<GameMode, MutableList<Question>>>()
            for (diffKey in rootObj.keys()) {
                val difficulty = Difficulty.valueOf(diffKey)
                val modeObj = rootObj.getJSONObject(diffKey)
                val modeMap = mutableMapOf<GameMode, MutableList<Question>>()
                for (modeKey in modeObj.keys()) {
                    val gameMode = GameMode.valueOf(modeKey)
                    val questionsArray = modeObj.getJSONArray(modeKey)
                    val questionList = mutableListOf<Question>()
                    for (i in 0 until questionsArray.length()) {
                        val qObj = questionsArray.getJSONObject(i)
                        val questionText = qObj.getString("question")
                        val islandsArray = qObj.getJSONArray("islands")
                        val islands = mutableListOf<Island>()
                        for (j in 0 until islandsArray.length()) {
                            val islandObj = islandsArray.getJSONObject(j)
                            val id = islandObj.getInt("id")
                            val label = islandObj.getString("label")
                            val correct = islandObj.getBoolean("correct")
                            islands.add(Island(id, label, correct))
                        }
                        questionList.add(questionText to islands)
                    }
                    modeMap[gameMode] = questionList
                }
                result[difficulty] = modeMap
            }
            // Convert mutable maps to immutable ones for safety
            result.mapValues { (_, modeMap) ->
                modeMap.mapValues { (_, list) -> list.toList() }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            emptyMap()
        }
    }

    private fun shuffleIslands(questions: List<Question>): List<Question> =
        questions.map { (prompt, islands) -> prompt to islands.shuffled() }

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private var currentMode = GameMode.SHAPES
    private var currentDifficultyIndex = 0
    private var currentQuestions = emptyList<Question>()
    private var currentQuestionIndex = 0
    private var correctCount = 0
    private var streak = 0

    private val allDifficultyOrder = listOf(
        Difficulty.STARTER, Difficulty.EASY, Difficulty.MEDIUM
    )

    fun loadQuestionsForMode(mode: GameMode) {
        currentMode = mode
        currentDifficultyIndex = 0
        currentQuestions = shuffleIslands(
            questionsByDifficulty[Difficulty.STARTER]?.get(mode) ?: emptyList<Question>()
        )
        currentQuestionIndex = 0
        correctCount = 0
        streak = 0
        _score.value = 0
    }

    fun getCurrentQuestion(): Question? =
        currentQuestions.getOrNull(currentQuestionIndex)

    fun hasMoreQuestions(): Boolean =
        currentQuestionIndex < currentQuestions.lastIndex

    fun advanceToNextQuestion() {
        currentQuestionIndex++
    }

    internal fun recordAnswer(isCorrect: Boolean): StreakResult {
        if (!isCorrect) {
            streak = 0
            return StreakResult(0, false, false)
        }

        streak++
        correctCount++
        _score.value += 1

        val bonusAwarded = streak % 3 == 0
        if (bonusAwarded) {
            _score.value += 1
        }

        val oldDifficultyIndex = currentDifficultyIndex
        updateDifficulty()
        val difficultyAdvanced = currentDifficultyIndex > oldDifficultyIndex

        return StreakResult(streak, bonusAwarded, difficultyAdvanced)
    }

    private fun updateDifficulty() {
        val newIndex = when {
            correctCount >= 10 -> 2
            correctCount >= 5 -> 1
            else -> 0
        }
        if (newIndex > currentDifficultyIndex) {
            currentDifficultyIndex = newIndex
            val newDifficulty = allDifficultyOrder[currentDifficultyIndex]
            currentQuestions = shuffleIslands(
                questionsByDifficulty[newDifficulty]?.get(currentMode) ?: currentQuestions
            )
            currentQuestionIndex = 0
        }
    }

    fun getCurrentDifficulty(): Difficulty = allDifficultyOrder[currentDifficultyIndex]

    fun reset() {
        currentDifficultyIndex = 0
        currentQuestions = emptyList<Question>()
        currentQuestionIndex = 0
        correctCount = 0
        streak = 0
        _score.value = 0
    }
}

internal data class StreakResult(
    val streak: Int,
    val bonusAwarded: Boolean,
    val difficultyAdvanced: Boolean
)
