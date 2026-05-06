package com.example.mohamedazizfekih.model

// Based on Lab 4.3 - Classes: a category groups related questions.
data class HeritageCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val questions: List<Question>
)
