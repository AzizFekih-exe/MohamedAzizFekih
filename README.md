# Tunisia Heritage Quest - Image Identification Game

Android quiz game built with Kotlin, Jetpack Compose, MVVM, and Navigation Compose.

## Project Structure

- `app/src/main/java/com/example/mohamedazizfekih/MainActivity.kt` - app entry point.
- `app/src/main/java/com/example/mohamedazizfekih/model/` - `Question`, `Difficulty`, and category models.
- `app/src/main/java/com/example/mohamedazizfekih/data/HeritageRepository.kt` - six categories and quiz data.
- `app/src/main/java/com/example/mohamedazizfekih/viewmodel/` - quiz state and `QuizViewModel`.
- `app/src/main/java/com/example/mohamedazizfekih/navigation/QuestNavGraph.kt` - Navigation Compose setup.
- `app/src/main/java/com/example/mohamedazizfekih/ui/screens/` - category, difficulty, quiz, and result screens.
- `app/src/main/java/com/example/mohamedazizfekih/ui/components/` - reusable image and answer components.
- `app/src/main/res/drawable/placeholder.xml` - safe image placeholder.

## Image Instructions

The app currently uses `R.drawable.placeholder`, so it will not crash when real images are missing.

To add real images later:

1. Put image files in `app/src/main/res/drawable/`.
2. Use `.webp` or `.png`. WebP is recommended because it is smaller.
3. Use lowercase snake_case names with no spaces. The current Islamic Heritage image files are:
   - `kairouan_mosque.jpg`
   - `zitouna_mosque.jpeg`
   - `ribat_sousse.jpg`
   - `ribat_monastir.jpg`
   - `medina_tunis.jpg`
   - `kairouan_medina.jpg`
   - `sousse_medina.webp`
   - `great_mosque_sfax.jpg`
   - `great_mosque_mahdia.jpeg`
   - `youssef_dey_mosque.jpeg`
   - `sidi_mahriz_mosque.jpg`
   - `three_doors_mosque.jpg`
4. In `HeritageRepository.kt`, replace:

```kotlin
imageRes = R.drawable.placeholder
```

with:

```kotlin
imageRes = R.drawable.kairouan_mosque
```

Each question has a `// TODO: Replace with real image in drawable` style comment showing the recommended future file name.

## Run

Open the project in Android Studio, wait for Gradle sync, then run the `app` configuration on an emulator or device.

From a terminal:

```powershell
.\gradlew.bat :app:assembleDebug
```
