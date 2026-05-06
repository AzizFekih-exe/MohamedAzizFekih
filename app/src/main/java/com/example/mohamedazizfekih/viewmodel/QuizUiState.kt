package com.example.mohamedazizfekih.viewmodel

import com.example.mohamedazizfekih.model.Difficulty
import com.example.mohamedazizfekih.model.Question

// Based on Lab 7.1 - Data class: one object stores all quiz screen state.
data class QuizUiState(
    val categoryId: String = "",
    val categoryTitle: String = "",
    val difficulty: Difficulty = Difficulty.EASY,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val currentStreak: Int = 0,
    val selectedAnswer: String? = null,
    val timeLeft: Int = QuizViewModel.SECONDS_PER_QUESTION,
    val isFinished: Boolean = false
) {
    val totalQuestions: Int
        get() = questions.size

    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val progressText: String
        get() = "${(currentQuestionIndex + 1).coerceAtMost(totalQuestions)}/$totalQuestions"

    val percentage: Int
        get() = if (maxScore == 0) 0 else (score * 100) / maxScore

    val maxScore: Int
        get() = totalQuestions * QuizViewModel.POINTS_PER_CORRECT_ANSWER

    val progressValue: Float
        get() = if (totalQuestions == 0) 0f else (currentQuestionIndex + 1).toFloat() / totalQuestions

    val hasSavedProgress: Boolean
        get() = questions.isNotEmpty() && !isFinished
}
