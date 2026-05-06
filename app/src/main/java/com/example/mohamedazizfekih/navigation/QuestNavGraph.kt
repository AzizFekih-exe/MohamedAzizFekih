package com.example.mohamedazizfekih.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mohamedazizfekih.data.HeritageRepository
import com.example.mohamedazizfekih.model.AppSettings
import com.example.mohamedazizfekih.model.Difficulty
import com.example.mohamedazizfekih.ui.components.ScreenEnterAnimation
import com.example.mohamedazizfekih.ui.screens.AnswerResultScreen
import com.example.mohamedazizfekih.ui.screens.CategoryScreen
import com.example.mohamedazizfekih.ui.screens.DifficultyScreen
import com.example.mohamedazizfekih.ui.screens.HomeScreen
import com.example.mohamedazizfekih.ui.screens.QuizScreen
import com.example.mohamedazizfekih.ui.screens.ResultScreen
import com.example.mohamedazizfekih.ui.screens.SettingsScreen
import com.example.mohamedazizfekih.viewmodel.QuizViewModel

// Based on Lab 11.1 - Navigation Compose: routes connect app screens.
object QuestRoutes {
    const val HOME = "home"
    const val CATEGORIES = "categories"
    const val DIFFICULTY = "difficulty/{categoryId}"
    const val QUIZ = "quiz/{categoryId}/{difficulty}"
    const val ANSWER_RESULT = "answer_result"
    const val RESULT = "result"
    const val SETTINGS = "settings"

    fun difficulty(categoryId: String): String = "difficulty/$categoryId"
    fun quiz(categoryId: String, difficulty: Difficulty): String = "quiz/$categoryId/${difficulty.name}"
}

// Based on Lab 11.1 - Navigation Compose and Lab 9.2 - Animation.
@Composable
fun QuestNavGraph(
    navController: NavHostController = rememberNavController(),
    quizViewModel: QuizViewModel = viewModel(),
    isDarkTheme: Boolean,
    onThemeChange: () -> Unit,
    settings: AppSettings,
    onTimerChange: (Boolean) -> Unit,
    onSoundChange: (Boolean) -> Unit,
    onHapticChange: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = QuestRoutes.HOME,
        // Based on Lab 9.2 - Simple Animation: fade and slide screens smoothly.
        enterTransition = {
            fadeIn(animationSpec = tween(280)) +
                slideInHorizontally(animationSpec = tween(280)) { width -> width / 3 }
        },
        exitTransition = {
            fadeOut(animationSpec = tween(160)) +
                slideOutHorizontally(animationSpec = tween(160)) { width -> -width / 5 }
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(280)) +
                slideInHorizontally(animationSpec = tween(280)) { width -> -width / 3 }
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(160)) +
                slideOutHorizontally(animationSpec = tween(160)) { width -> width / 5 }
        }
    ) {
        composable(QuestRoutes.HOME) {
            val playerStats = quizViewModel.playerStats.collectAsState().value
            val siteCount = HeritageRepository
                .getCategory(HeritageRepository.ISLAMIC_HERITAGE_ID)
                ?.questions
                ?.size ?: 0

            ScreenEnterAnimation {
                HomeScreen(
                    siteCount = siteCount,
                    masteryPercent = playerStats.masteryPercent,
                    streakCount = playerStats.currentStreak,
                    onSettingsClick = {
                        navController.navigate(QuestRoutes.SETTINGS)
                    },
                    onStartClick = {
                        navController.navigate(QuestRoutes.CATEGORIES)
                    }
                )
            }
        }

        composable(QuestRoutes.CATEGORIES) {
            ScreenEnterAnimation {
                CategoryScreen(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = onThemeChange,
                    onSettingsClick = {
                        navController.navigate(QuestRoutes.SETTINGS)
                    },
                    onCategorySelected = { categoryId ->
                        navController.navigate(QuestRoutes.difficulty(categoryId))
                    }
                )
            }
        }

        composable(QuestRoutes.DIFFICULTY) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId").orEmpty()
            val quizUiState = quizViewModel.uiState.collectAsState().value
            ScreenEnterAnimation {
                DifficultyScreen(
                    categoryId = categoryId,
                    quizUiState = quizUiState,
                    onResetProgress = quizViewModel::resetSavedProgress,
                    onSettingsClick = {
                        navController.navigate(QuestRoutes.SETTINGS)
                    },
                    onDifficultySelected = { selectedCategoryId, difficulty ->
                        navController.navigate(QuestRoutes.quiz(selectedCategoryId, difficulty))
                    }
                )
            }
        }

        composable(QuestRoutes.QUIZ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId").orEmpty()
            val difficulty = backStackEntry.arguments?.getString("difficulty").orEmpty()
            ScreenEnterAnimation {
                QuizScreen(
                    categoryId = categoryId,
                    difficultyName = difficulty,
                    viewModel = quizViewModel,
                    settings = settings,
                    onSettingsClick = {
                        navController.navigate(QuestRoutes.SETTINGS)
                    },
                    onAnswerSubmitted = {
                        navController.navigate(QuestRoutes.ANSWER_RESULT)
                    },
                    onQuizFinished = {
                        navController.navigate(QuestRoutes.RESULT) {
                            popUpTo(QuestRoutes.QUIZ) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(QuestRoutes.ANSWER_RESULT) {
            ScreenEnterAnimation {
                AnswerResultScreen(
                    viewModel = quizViewModel,
                    onSettingsClick = {
                        navController.navigate(QuestRoutes.SETTINGS)
                    },
                    onNextQuestion = { isLastQuestion ->
                        quizViewModel.goToNextQuestion()
                        if (isLastQuestion) {
                            navController.navigate(QuestRoutes.RESULT) {
                                popUpTo(QuestRoutes.QUIZ) { inclusive = true }
                            }
                        } else {
                            navController.popBackStack()
                        }
                    }
                )
            }
        }

        composable(QuestRoutes.RESULT) {
            ScreenEnterAnimation {
                ResultScreen(
                    viewModel = quizViewModel,
                    onSettingsClick = {
                        navController.navigate(QuestRoutes.SETTINGS)
                    },
                    onPlayAgain = {
                        navController.navigate(QuestRoutes.HOME) {
                            popUpTo(QuestRoutes.HOME) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(QuestRoutes.SETTINGS) {
            ScreenEnterAnimation {
                SettingsScreen(
                    isDarkTheme = isDarkTheme,
                    settings = settings,
                    onThemeChange = onThemeChange,
                    onTimerChange = onTimerChange,
                    onSoundChange = onSoundChange,
                    onHapticChange = onHapticChange,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
