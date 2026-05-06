package com.example.mohamedazizfekih.model

// Based on Lab 7.1 - Data class: stores one quiz question in a simple model.
data class Question(
    val imageRes: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val difficulty: Difficulty
)
