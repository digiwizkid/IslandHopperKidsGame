# Island Hopper 🏝️

A fun educational Android game for kids (ages 4–6) to learn shapes, letters, and numbers by hopping between islands!

Built with **Kotlin**, **Jetpack Compose**, and **Unidirectional Data Flow (UDF)**.

## Features

- **3 game modes**: Shapes, Letters, Numbers
- **3 difficulty tiers**: Starter → Easy → Medium (auto-advance at 5 and 10 correct answers)
- **226 questions** across all tiers, loaded from `assets/questions.json` — no recompile to edit
- **Circular answer buttons**: 140–155 dp tap targets with vibrant rotating colours
- **Score + streak**: always-visible star icon and 🔥 counter (bonus point every 3‑streak)
- **Shake animation** on wrong answers, **particle burst** on correct
- **Sound effects** (correct/wrong/streak/game‑over) + **haptic feedback**
- **Timer mode** (⏱ 30 s countdown)
- **Level‑up overlay** on difficulty advance
- **Shuffled answers** — island order randomised each session
- **Kid‑friendly ocean palette** with fade transitions

## Difficulty Tiers

| Tier | Unlock | Questions | Example Topics |
|------|--------|-----------|----------------|
| **Starter** | Start | 76 | Basic shape ID, numbers 1–10, letters A–Z |
| **Easy** | 5 correct | 75 | Real‑world shapes, addition (1+1…2+3), numbers 11–19, initial letter sounds |
| **Medium** | 10 correct | 75 | 3‑D shapes (sphere/cube/cone/cylinder), add/sub to 10, letter order, vowels, phonics |

Every tier has its own unique question set.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.0.20 |
| UI | Jetpack Compose + Material 3 |
| Architecture | UDF (ViewModel + StateFlow) |
| Navigation | Navigation Compose + fade transitions |
| Sound | ToneGenerator + Vibrator (no audio files) |
| Data | JSON asset + Android Assets API |
| Build | Gradle KTS + Version Catalog |

## Project Structure

```
com.digiwizkid.islandhopper/
├── data/
│   ├── models/          # GameMode, Island, GameUiState, Difficulty
│   └── repository/      # GameRepository (JSON loader), SoundManager
├── ui/
│   ├── theme/           # Colour, Typography, Theme
│   ├── screens/
│   │   ├── home/        # Mode selection (with timer toggle)
│   │   ├── game/        # Gameplay screen + ViewModel
│   │   └── result/      # Score result screen
│   └── components/      # IslandButton, CharacterAnimation, ParticleEffect, QuestionPrompt, ScoreDisplay
└── navigation/          # Screen routes + animated NavGraph
```

## Getting Started

1. Open the project in Android Studio (Meerkat or newer)
2. Sync Gradle
3. Run on an emulator or device (API 26+)

### Prerequisites

- Android Studio Meerkat | 2024.3+
- JDK 17

## Gameplay

1. Pick a mode (Shapes, Letters, Numbers) — tap ⏱ for timer mode
2. Read the question
3. Tap the correct circular island to score + build your streak
4. A wrong answer shakes the island and resets your streak
5. The difficulty label advances at 5 and 10 correct answers
6. Answer every question or let the timer run out to see your final score

## Editing Questions

Open `app/src/main/assets/questions.json`. Each entry has a `"question"` string and an `"islands"` array with `id`, `label`, and `correct` fields. No recompile needed.

## Running Tests

```bash
# Unit tests (Robolectric)
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest
```

## License

Educational purposes.
