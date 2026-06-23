# Implementation Plan: Island Hopper Digital Android App

This target-focused implementation plan defines a complete, scalable architectural framework based on native Android best practices: **Kotlin, Unidirectional Data Flow (UDF), and Jetpack Compose**. It is structured optimally for ingestion by an LLM or advanced code-generation engine to produce production-ready code.

---

## 🏗️ Phase 1: Architecture & State Definition ✅

### 1. Game State Structure ✅
```kotlin
enum class GameMode { SHAPES, LETTERS, NUMBERS }

data class Island(
    val id: Int, val label: String,
    val isCorrect: Boolean, val isEnabled: Boolean = true
)

data class GameUiState(
    val score: Int = 0, val currentMode: GameMode? = null,
    val questionPrompt: String = "", val activeIslands: List<Island> = emptyList(),
    val characterPosition: Int = 0, val isGameOver: Boolean = false,
    val triggerShakeId: Int? = null,
    val streak: Int = 0, val bestStreak: Int = 0,
    val difficulty: Difficulty = Difficulty.STARTER,
    val isTimerMode: Boolean = false, val timeRemaining: Int = 30,
    val showParticles: Boolean = false, val lastAnswerCorrect: Boolean? = null,
    val isMusicOn: Boolean = true
)
```

### 2. Project Scaffolding ✅
- Kotlin + Jetpack Compose project structure
- Gradle version catalog with BOM, Navigation, Lifecycle
- Material3 theming with ocean/kid-friendly color palette
- Navigation Compose with 4 routes: Home → Game/TimerGame → Result

### 3. Game Logic Layer ✅
- `GameRepository` with 5-tier question bank (STARTER/EASY/MEDIUM/HARD/EXPERT), 75 questions total
- `GameViewModel` exposing `StateFlow<GameUiState>` (UDF pattern)
- Correct/Wrong answer handling with score, streak, difficulty-advance tracking

---

## 🚀 Phase 2: UI & Interaction ✅

### 1. Enhanced Animations ✅
- **Character hop**: `CharacterAnimation` animates X/Y offset with arc trajectory
- **Particle effects**: `ParticleEffect` composable shows celebratory emoji burst on correct answers
- **Screen transitions**: `NavGraph` uses `slideIntoContainer`/`slideOutOfContainer` + `fadeIn`/`fadeOut`

### 2. Sound & Feedback ✅
- **Sound effects**: `SoundManager` uses `ToneGenerator` for correct (ACK), wrong (NACK), streak, and game-over tones
- **Music toggle**: `IconButton` on game screen toggles `MusicNote`/`MusicOff` icon, controls `SoundManager`
- **Haptic feedback**: `Vibrator` API triggered on correct and wrong selections

### 3. Difficulty Progression ✅
- **Adaptive questions**: `GameRepository` serves STARTER → EASY → MEDIUM → HARD → EXPERT, unlocks every 5 correct answers
- **Timer mode**: Countdown from 30s, game over when time expires; accessible via ⏱ button on HomeScreen
- **Streak bonuses**: Every 3 consecutive correct answers awards +1 bonus point; streak displayed as 🔥 counter
- **Difficulty-advance fix**: `StreakResult.difficultyAdvanced` flag prevents skipping the first question when a new tier loads

---

## 🌊 Phase 3: Persistence (Future)

- High score tracking with Room database
- Game progress saving
