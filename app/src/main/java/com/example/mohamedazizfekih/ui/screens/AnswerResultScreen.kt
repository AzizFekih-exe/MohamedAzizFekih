package com.example.mohamedazizfekih.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mohamedazizfekih.ui.components.QuestionImage
import com.example.mohamedazizfekih.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
// Based on Lab 11.1 - Navigation Compose and Lab 3.1 - Column/Text.
@Composable
fun AnswerResultScreen(
    viewModel: QuizViewModel,
    onSettingsClick: () -> Unit,
    onNextQuestion: (Boolean) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    val question = uiState.currentQuestion
    val selectedAnswer = uiState.selectedAnswer
    val isCorrect = question != null && selectedAnswer == question.correctAnswer

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = if (isCorrect) "Correct Answer" else "Review Answer"
                    Text(text = title)
                },
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
        if (question == null || selectedAnswer == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No answer to review.")
            }
            return@Scaffold
        }

        val isLastQuestion = uiState.currentQuestionIndex >= uiState.totalQuestions - 1

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuestionImage(
                imageRes = question.imageRes,
                height = 230.dp
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(
                        imageVector = if (isCorrect) Icons.Filled.CheckCircle else Icons.Filled.Close,
                        contentDescription = null,
                        tint = if (isCorrect) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )

                    if (isCorrect) {
                        Text(
                            text = "Correct answer",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "+10 points",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    } else {
                        Text(
                            text = "Incorrect",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Correct answer: ${question.correctAnswer}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Explanation: the picture shows ${question.correctAnswer}. Compare the shape, entrance, or city name with the image before choosing.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Button(
                onClick = { onNextQuestion(isLastQuestion) },
                modifier = Modifier.fillMaxWidth()
            ) {
                val buttonText = if (isLastQuestion) "See Final Score" else "Next Question"
                Text(text = buttonText)
            }
        }
    }
}
