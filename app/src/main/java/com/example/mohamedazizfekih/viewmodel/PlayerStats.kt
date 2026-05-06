package com.example.mohamedazizfekih.viewmodel

// Based on Lab 7.1 - Data class: stores the player's overall progress.
data class PlayerStats(
    val answeredQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val currentStreak: Int = 0
) {
    val masteryPercent: Int
        get() = if (answeredQuestions == 0) 0 else (correctAnswers * 100) / answeredQuestions
}
