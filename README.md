# Tunisia Heritage Quest - Image Identification Game

## Project Overview

Tunisia Heritage Quest is an Android image identification quiz game. The player chooses a heritage category and difficulty, then identifies Tunisian monuments, medinas, mosques, and historic places from images.

The completed playable category is **Islamic Heritage**. It contains real quiz questions with multiple difficulty levels. The other categories are included in the app structure, but they currently show a **Coming Soon** popup because their real question data is not finished yet.

Main features:

- Initial home screen with sites, mastery, and streak stats
- Category and difficulty selection
- Multiple choice questions with 4 answers
- Submit Answer button before checking the answer
- Correct and incorrect answer result screens
- Explanation after wrong answers
- 10 points for each correct answer
- Final score, percentage, and performance message
- Optional timer
- Sound and haptic feedback toggles
- Light and dark theme
- Navigation animations
- Saved unfinished quiz progress
- Shuffled questions and shuffled answer options

## Architecture Explanation

The app uses a simple MVVM architecture.

**Model**

The model package contains the data classes and enums used by the app:

- `Question`
- `Difficulty`
- `HeritageCategory`
- `AppSettings`

These classes represent the quiz data, category data, difficulty levels, and app settings.

**Data Source**

`HeritageRepository.kt` stores the local quiz data. It contains the six required categories. Only Islamic Heritage has real questions. The other categories are kept as placeholders for future expansion.

**ViewModel**

`QuizViewModel.kt` stores and updates the quiz state. It controls:

- Starting a quiz
- Selecting/submitting answers
- Score calculation
- Timer updates
- Moving to the next question
- Resetting quiz progress
- Player stats for mastery and streak

`QuizUiState.kt` stores the current quiz screen state. `PlayerStats.kt` stores overall home screen stats that should not disappear when the current quiz is reset.

**UI**

The UI is built with Jetpack Compose and Material 3. Screens are inside:

```text
app/src/main/java/com/example/mohamedazizfekih/ui/screens/
```

Important screens:

- `HomeScreen.kt`
- `CategoryScreen.kt`
- `DifficultyScreen.kt`
- `QuizScreen.kt`
- `AnswerResultScreen.kt`
- `ResultScreen.kt`
- `SettingsScreen.kt`

Reusable UI parts are inside:

```text
app/src/main/java/com/example/mohamedazizfekih/ui/components/
```

**Navigation**

Navigation is handled in `QuestNavGraph.kt` using Navigation Compose. The app starts at the home screen, then moves through categories, difficulty, quiz, answer result, and final result screens.

## Setup Instructions

Requirements:

- Android Studio
- JDK 17
- Android SDK installed
- Emulator or physical Android device

Steps:

1. Open the project folder in Android Studio.
2. Wait for Gradle sync to finish.
3. Choose an emulator or connected device.
4. Run the `app` configuration.

You can also build from the terminal:

```powershell
.\gradlew.bat :app:assembleDebug
```

Run unit tests:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

Build Android UI tests:

```powershell
.\gradlew.bat :app:assembleDebugAndroidTest
```

Image notes:

- Images should be placed in `app/src/main/res/drawable/`.
- Use lowercase names with underscores, for example `kairouan_mosque.jpg`.
- Recommended formats are `.webp`, `.png`, `.jpg`, or `.jpeg`.
- The app uses drawable resource references like `R.drawable.kairouan_mosque`.

## Known Issues

- Only **Islamic Heritage** is fully playable.
- Roman Heritage, Punic & Pre-Roman, Modern Heritage, Natural & Mixed Sites, and Extra Placeholder Category currently show a **Coming Soon** popup.
- Mastery and streak are saved only while the app process is open. They are not saved permanently after closing the app completely.
- Sound feedback uses Android built-in `ToneGenerator` sounds, so the exact sound can be slightly different depending on the device.
- Haptic feedback can also feel different depending on the phone or emulator.
- There is no online database or API. All quiz data is stored locally in the project.
