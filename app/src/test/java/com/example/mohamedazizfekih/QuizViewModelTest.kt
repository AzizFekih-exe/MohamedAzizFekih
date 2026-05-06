package com.example.mohamedazizfekih

import com.example.mohamedazizfekih.data.HeritageRepository
import com.example.mohamedazizfekih.model.Difficulty
import com.example.mohamedazizfekih.viewmodel.QuizViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

// Based on Lab 10.3 - ViewModel test: quiz logic is tested without opening the UI.
class QuizViewModelTest {
    @Test
    fun correctAnswer_increasesScore() {
        val viewModel = QuizViewModel()

        viewModel.startGame(
            categoryId = HeritageRepository.ISLAMIC_HERITAGE_ID,
            difficultyName = Difficulty.EASY.name
        )

        val correctAnswer = viewModel.uiState.value.currentQuestion?.correctAnswer.orEmpty()
        viewModel.selectAnswer(correctAnswer)

        assertEquals(10, viewModel.uiState.value.score)
    }

    @Test
    fun correctAnswer_updatesPlayerStats() {
        val viewModel = QuizViewModel()

        viewModel.startGame(
            categoryId = HeritageRepository.ISLAMIC_HERITAGE_ID,
            difficultyName = Difficulty.EASY.name
        )

        val correctAnswer = viewModel.uiState.value.currentQuestion?.correctAnswer.orEmpty()
        viewModel.selectAnswer(correctAnswer)

        assertEquals(1, viewModel.playerStats.value.answeredQuestions)
        assertEquals(100, viewModel.playerStats.value.masteryPercent)
        assertEquals(1, viewModel.playerStats.value.currentStreak)
    }
}
