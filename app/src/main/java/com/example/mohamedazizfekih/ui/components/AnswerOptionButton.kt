package com.example.mohamedazizfekih.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

// Based on Lab 5.1 - Buttons and Lab 4.1 - Conditionals for answer colors.
@Composable
fun AnswerOptionButton(
    answer: String,
    correctAnswer: String,
    selectedAnswer: String?,
    isSubmitted: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = selectedAnswer == answer
    val isCorrect = isSubmitted && answer == correctAnswer
    val isWrongSelection = isSubmitted && isSelected && !isCorrect

    val colors = when {
        isCorrect -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )
        isWrongSelection -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
        isSelected -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
        else -> ButtonDefaults.outlinedButtonColors()
    }

    // Based on Lab 5.1 - Buttons: answer choices are interactive buttons.
    if (!isSelected && !isSubmitted) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Answer option $answer" }
        ) {
            Text(text = answer)
        }
    } else {
        Button(
            onClick = onClick,
            colors = colors,
            modifier = modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Answered option $answer" }
        ) {
            Text(text = answer)
        }
    }
}
