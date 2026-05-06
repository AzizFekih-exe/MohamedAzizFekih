package com.example.mohamedazizfekih.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mohamedazizfekih.data.HeritageRepository
import com.example.mohamedazizfekih.model.Difficulty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Based on Lab 10.2 - ViewModel: stores quiz state outside composables.
class QuizViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val _playerStats = MutableStateFlow(PlayerStats())
    val playerStats: StateFlow<PlayerStats> = _playerStats.asStateFlow()

    // Based on Lab 10.2 - ViewModel and StateFlow, and Lab 7.3 - shuffled collection.
    fun startGame(categoryId: String, difficultyName: String) {
        val difficulty = difficultyName.toDifficulty()
        val currentState = _uiState.value

        // Resume saved progress if the same quiz was already started.
        if (
            currentState.hasSavedProgress &&
            currentState.categoryId == categoryId &&
            currentState.difficulty == difficulty
        ) {
            return
        }

        val category = HeritageRepository.getCategory(categoryId)
        val questions = HeritageRepository
            .questionsFor(categoryId, difficulty)
            .map { question -> question.copy(options = question.options.shuffled()) }
            .shuffled()

        _uiState.value = QuizUiState(
            categoryId = categoryId,
            categoryTitle = category?.title.orEmpty(),
            difficulty = difficulty,
            questions = questions,
            timeLeft = SECONDS_PER_QUESTION,
            isFinished = questions.isEmpty()
        )
    }

    // Based on Lab 4.1 - Conditionals: checks if the selected answer is correct.
    fun selectAnswer(answer: String) {
        val state = _uiState.value
        val currentQuestion = state.currentQuestion

        // Based on Lab 4.1 - Conditionals: check if the selected answer is correct.
        if (state.selectedAnswer != null || currentQuestion == null) {
            return
        }

        val isCorrect = answer == currentQuestion.correctAnswer
        _uiState.value = state.copy(
            selectedAnswer = answer,
            score = if (isCorrect) state.score + POINTS_PER_CORRECT_ANSWER else state.score,
            currentStreak = if (isCorrect) state.currentStreak + 1 else 0
        )

        // Based on Lab 10.2 - ViewModel state: home screen stats update after each answer.
        val oldStats = _playerStats.value
        _playerStats.value = oldStats.copy(
            answeredQuestions = oldStats.answeredQuestions + 1,
            correctAnswers = if (isCorrect) oldStats.correctAnswers + 1 else oldStats.correctAnswers,
            currentStreak = if (isCorrect) oldStats.currentStreak + 1 else 0
        )
    }

    // Based on Lab 4.1 - Conditionals: moves forward or finishes the quiz.
    fun goToNextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentQuestionIndex + 1

        // Based on Lab 4.1 - Conditionals: finish when there are no questions left.
        if (nextIndex >= state.totalQuestions) {
            _uiState.value = state.copy(isFinished = true)
        } else {
            _uiState.value = state.copy(
                currentQuestionIndex = nextIndex,
                selectedAnswer = null,
                timeLeft = SECONDS_PER_QUESTION
            )
        }
    }

    // Based on Lab 6.1 - State: the timer updates the UI state every second.
    fun tickTimer() {
        val state = _uiState.value

        if (state.isFinished || state.selectedAnswer != null) {
            return
        }

        if (state.timeLeft <= 1) {
            val nextIndex = state.currentQuestionIndex + 1
            if (nextIndex >= state.totalQuestions) {
                _uiState.value = state.copy(timeLeft = 0, isFinished = true)
            } else {
                _uiState.value = state.copy(
                    currentQuestionIndex = nextIndex,
                    selectedAnswer = null,
                    timeLeft = SECONDS_PER_QUESTION
                )
            }
        } else {
            _uiState.value = state.copy(timeLeft = state.timeLeft - 1)
        }
    }

    // Based on Lab 10.2 - ViewModel: resets all state after a finished quiz.
    fun resetQuiz() {
        _uiState.value = QuizUiState()
    }

    // Based on Lab 10.2 - ViewModel: clears saved unfinished progress.
    fun resetSavedProgress() {
        _uiState.value = QuizUiState()
    }

    // Based on Lab 4.1 - Conditionals: chooses the final performance message.
    fun performanceMessage(): String {
        val percentage = _uiState.value.percentage

        // Based on Lab 4.1 - Conditionals: choose a message from the final percentage.
        return when {
            percentage >= 90 -> "Outstanding heritage expert!"
            percentage >= 70 -> "Great work, you know Tunisia well."
            percentage >= 50 -> "Good start, keep exploring."
            else -> "Try again and learn the landmarks step by step."
        }
    }

    // Based on Lab 7.1 - Enum: converts a route String back into a Difficulty value.
    private fun String.toDifficulty(): Difficulty {
        return Difficulty.entries.firstOrNull { difficulty ->
            difficulty.name.equals(this, ignoreCase = true)
        } ?: Difficulty.EASY
    }

    companion object {
        const val SECONDS_PER_QUESTION = 15
        const val POINTS_PER_CORRECT_ANSWER = 10
    }
}
