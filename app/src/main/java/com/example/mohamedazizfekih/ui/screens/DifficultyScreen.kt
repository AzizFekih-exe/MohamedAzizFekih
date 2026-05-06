package com.example.mohamedazizfekih.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mohamedazizfekih.model.Difficulty
import com.example.mohamedazizfekih.viewmodel.QuizUiState

@OptIn(ExperimentalMaterial3Api::class)
// Based on Lab 8.1 - LazyColumn and Lab 6.1 - State shown in the UI.
@Composable
fun DifficultyScreen(
    categoryId: String,
    quizUiState: QuizUiState,
    onResetProgress: () -> Unit,
    onSettingsClick: () -> Unit,
    onDifficultySelected: (String, Difficulty) -> Unit
) {
    val difficulties = Difficulty.entries

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Choose Difficulty") },
                actions = {
                    // Based on Lab 9.2 - Material icons: gear opens settings.
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Based on Lab 8.1 - LazyColumn: difficulty options are displayed in a scrolling list.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(difficulties) { difficulty ->
                val showSavedProgress =
                    quizUiState.hasSavedProgress &&
                        quizUiState.categoryId == categoryId &&
                        quizUiState.difficulty == difficulty

                DifficultyCard(
                    difficulty = difficulty,
                    showSavedProgress = showSavedProgress,
                    quizUiState = quizUiState,
                    onResetProgress = onResetProgress,
                    onClick = { onDifficultySelected(categoryId, difficulty) }
                )
            }
        }
    }
}

// Based on Lab 3.1 - Text/Column and Lab 5.1 - Button.
@Composable
private fun DifficultyCard(
    difficulty: Difficulty,
    showSavedProgress: Boolean,
    quizUiState: QuizUiState,
    onResetProgress: () -> Unit,
    onClick: () -> Unit
) {
    val helperText = when (difficulty) {
        Difficulty.EASY -> "Clear landmarks and familiar places"
        Difficulty.MEDIUM -> "More specific cities and monuments"
        Difficulty.HARD -> "Detailed heritage knowledge"
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = difficulty.name.lowercase().replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = helperText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (showSavedProgress) {
                SavedDifficultyProgress(
                    quizUiState = quizUiState,
                    onResetProgress = onResetProgress
                )
            }
            // Based on Lab 5.1 - Buttons: start the selected quiz level.
            Button(onClick = onClick) {
                val buttonText = if (showSavedProgress) "Continue" else "Start"
                Text(text = buttonText)
            }
        }
    }
}

// Based on Lab 6.1 - State and Lab 9.1 - Material progress indicator.
@Composable
private fun SavedDifficultyProgress(
    quizUiState: QuizUiState,
    onResetProgress: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Saved progress: ${quizUiState.progressText}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        LinearProgressIndicator(
            progress = { quizUiState.progressValue },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onResetProgress) {
            Text(text = "Reset Progress")
        }
    }
}
