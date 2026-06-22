# Island Hopper 🏝️

A fun educational Android game for kids to learn shapes, letters, and numbers by hopping between islands!

Built with **Kotlin**, **Jetpack Compose**, and **Unidirectional Data Flow (UDF)**.

## Features

- **Three game modes**: Shapes, Letters, and Numbers
- **Interactive island hopping**: Tap the correct island to advance
- **Score tracking**: Earn points for correct answers
- **Shake animation**: Visual feedback for wrong answers
- **Kid-friendly theme**: Bright ocean palette with playful UI

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose + Material3 |
| Architecture | UDF (ViewModel + StateFlow) |
| Navigation | Navigation Compose |
| Build | Gradle KTS + Version Catalog |

## Project Structure

```
com.digiwizkid.islandhopper/
├── data/
│   ├── models/          # GameMode, Island, GameUiState
│   └── repository/      # GameRepository (question bank)
├── ui/
│   ├── theme/           # Color, Typography, Theme
│   ├── screens/
│   │   ├── home/        # Mode selection screen
│   │   ├── game/        # Main gameplay screen
│   │   └── result/      # Score result screen
│   └── components/      # Reusable UI components
└── navigation/          # Screen routes + NavGraph
```

## Getting Started

1. Open the project in Android Studio (Meerkat or newer)
2. Sync Gradle
3. Run on an emulator or device (API 26+)

### Prerequisites

- Android Studio Meerkat | 2024.3+
- JDK 17

## Gameplay

1. Select a game mode (Shapes, Letters, or Numbers)
2. Read the question prompt
3. Tap the correct island to score a point
4. Wrong answers shake the island — try again!
5. Complete all questions to see your final score

## Running Tests

```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest
```

## License

This project is for educational purposes.
