package com.example.mohamedazizfekih

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

// Based on Lab 11.2 - UI test: verifies a simple Navigation Compose flow.
class NavigationUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun categoryTap_opensDifficultyScreen() {
        composeRule.onNodeWithText("Start Quiz").performClick()
        composeRule.onNodeWithText("Islamic Heritage").performClick()
        composeRule.onNodeWithText("Choose Difficulty").assertExists()
    }
}
