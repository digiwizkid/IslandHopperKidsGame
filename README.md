# Island Hopper 🏝️

A fun educational Android game for kids to learn shapes, letters, and numbers by hopping between islands!

Built with **Kotlin**, **Jetpack Compose**, and **Unidirectional Data Flow (UDF)**.

## Features

- **Three game modes**: Shapes, Letters, and Numbers
- **5 difficulty levels**: Starter → Easy → Medium → Hard → Expert (auto-advances)
- **75 questions**: 5 age-appropriate questions per level per mode
- **Interactive island hopping**: Tap the correct island to advance
- **Score tracking**: Earn points for correct answers
- **Shake animation**: Visual feedback for wrong answers
- **Character hop**: Animated character arcs between islands
- **Particle effects**: Celebratory burst on correct answers
- **Sound effects**: Correct/wrong tones with haptic feedback
- **Music toggle**: On-screen control to mute/unmute audio
- **Streak bonuses**: 🔥 counter with bonus points every 3 correct
- **Adaptive difficulty**: Questions automatically get harder as you improve
- **Timer mode**: ⏱ countdown challenge for advanced play
- **Screen transitions**: Slide/fade animations between screens
- **Kid-friendly theme**: Bright ocean palette with playful UI

## Difficulty Levels

| Level | Age Group | Example Questions |
|-------|-----------|-------------------|
| **Starter** | 3-4 yrs | "Which one is round?", "Count the stars", "Find the letter A" |
| **Easy** | 4-5 yrs | "1 + 1 = ?", "Which shape has 4 sides?", "Letters in CAT" |
| **Medium** | 5-6 yrs | "2 + 3 = ?", "Which shape has 5 sides?", "Rhyming words" |
| **Hard** | 6-7 yrs | "10 - 4 = ?", "Silent K words", "3 × 2 = ?" |
| **Expert** | 7+ yrs | "7 × 3 = ?", "Plurals", "Fractions", "Geometry" |

Each level unlocks after 5 correct answers. A perfect run answers all 25 questions!

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose + Material3 |
| Architecture | UDF (ViewModel + StateFlow) |
| Navigation | Navigation Compose |
| Sound | ToneGenerator + Vibrator |
| Build | Gradle KTS + Version Catalog |

## Project Structure

```
com.digiwizkid.islandhopper/
├── data/
│   ├── models/          # GameMode, Island, GameUiState, Difficulty
│   └── repository/      # GameRepository (75 questions), SoundManager
├── ui/
│   ├── theme/           # Color, Typography, Theme
│   ├── screens/
│   │   ├── home/        # Mode selection screen (with timer toggle)
│   │   ├── game/        # Main gameplay screen
│   │   └── result/      # Score result screen
│   └── components/      # Animated island, character, particles
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

1. Select a game mode (Shapes, Letters, or Numbers) — or tap ⏱ for timer mode
2. Read the question prompt
3. Tap the correct island to score a point and build your streak
4. Wrong answers shake the island — streaks reset!
5. Difficulty auto-advances from Starter → Easy → Medium → Hard → Expert
6. Complete all 25 questions (or beat the timer) to see your final score

## Running Tests

```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest
```

## License

This project is for educational purposes.
