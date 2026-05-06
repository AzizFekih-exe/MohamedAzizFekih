package com.example.mohamedazizfekih.ui.screens

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mohamedazizfekih.model.AppSettings
import com.example.mohamedazizfekih.ui.components.AnswerOptionButton
import com.example.mohamedazizfekih.ui.components.QuestionImage
import com.example.mohamedazizfekih.viewmodel.QuizUiState
import com.example.mohamedazizfekih.viewmodel.QuizViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
// Based on Lab 10.2 - ViewModel, Lab 6.1 - State, and Lab 11.1 - Navigation.
@Composable
fun QuizScreen(
    categoryId: String,
    difficultyName: String,
    viewModel: QuizViewModel,
    settings: AppSettings,
    onSettingsClick: () -> Unit,
    onAnswerSubmitted: () -> Unit,
    onQuizFinished: () -> Unit
) {
    // Based on Lab 10.2 - ViewModel and StateFlow: collect quiz state in Compose.
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(categoryId, difficultyName) {
        viewModel.startGame(categoryId, difficultyName)
    }

    // Based on Lab 6.1 - State and the timer requirement: UI reacts to ViewModel state.
    LaunchedEffect(
        uiState.currentQuestionIndex,
        uiState.selectedAnswer,
        uiState.isFinished,
        settings.useTimer
    ) {
        while (settings.useTimer && !uiState.isFinished && uiState.selectedAnswer == null) {
            delay(1_000)
            viewModel.tickTimer()
        }
    }

    LaunchedEffect(uiState.isFinished) {
        if (uiState.isFinished) {
            onQuizFinished()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.categoryTitle.ifBlank { "Quiz" }) },
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
        QuizContent(
            uiState = uiState,
            settings = settings,
            onAnswerSelected = viewModel::selectAnswer,
            onAnswerSubmitted = onAnswerSubmitted,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

// Based on Lab 12.2 - Adaptive content: changes layout on wider screens.
@Composable
private fun QuizContent(
    uiState: QuizUiState,
    settings: AppSettings,
    onAnswerSelected: (String) -> Unit,
    onAnswerSubmitted: () -> Unit,
    modifier: Modifier = Modifier
) {
    val question = uiState.currentQuestion

    if (question == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "No questions available yet.")
        }
        return
    }

    val progress = if (uiState.totalQuestions == 0) {
        0f
    } else {
        (uiState.currentQuestionIndex + 1).toFloat() / uiState.totalQuestions.toFloat()
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val wideLayout = screenWidth >= 700
    val imageHeight = if (wideLayout) 300.dp else 220.dp
    val haptic = LocalHapticFeedback.current
    var selectedOption by remember(uiState.currentQuestionIndex) {
        mutableStateOf<String?>(null)
    }
    val toneGenerator = remember {
        ToneGenerator(AudioManager.STREAM_MUSIC, 80)
    }

    DisposableEffect(Unit) {
        onDispose {
            toneGenerator.release()
        }
    }

    fun submitAnswer() {
        val answer = selectedOption ?: return
        val isCorrect = answer == question.correctAnswer

        if (settings.hapticEnabled) {
            val feedbackType = if (isCorrect) {
                HapticFeedbackType.TextHandleMove
            } else {
                HapticFeedbackType.LongPress
            }
            haptic.performHapticFeedback(feedbackType)
        }

        if (settings.soundEnabled) {
            // Based on Lab 4.1 - Conditionals: different sounds for correct and wrong answers.
            val tone = if (isCorrect) {
                ToneGenerator.TONE_CDMA_CONFIRM
            } else {
                ToneGenerator.TONE_PROP_NACK
            }
            val duration = if (isCorrect) 220 else 180
            toneGenerator.startTone(tone, duration)
        }

        onAnswerSelected(answer)
        onAnswerSubmitted()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Progress ${uiState.progressText}", fontWeight = FontWeight.Bold)
            Text(text = "Score ${uiState.score}")
            if (settings.useTimer) {
                Text(text = "${uiState.timeLeft}s")
            } else {
                Text(text = "No timer")
            }
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )

        if (wideLayout) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuestionImage(
                        imageRes = question.imageRes,
                        height = imageHeight
                    )
                    QuestionText(text = question.question)
                }
                    AnswerList(
                        options = question.options,
                        selectedAnswer = selectedOption,
                        onAnswerSelected = { selectedOption = it },
                        onSubmitAnswer = ::submitAnswer,
                        modifier = Modifier.weight(1f)
                    )
            }
        } else {
            QuestionImage(
                imageRes = question.imageRes,
                height = imageHeight
            )
            QuestionText(text = question.question)
            AnswerList(
                options = question.options,
                selectedAnswer = selectedOption,
                onAnswerSelected = { selectedOption = it },
                onSubmitAnswer = ::submitAnswer
            )
        }
    }
}

// Based on Lab 3.1 - Text composable.
@Composable
private fun QuestionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

// Based on Lab 8.1 - repeated items and Lab 9.2 - animateContentSize.
@Composable
private fun AnswerList(
    options: List<String>,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onSubmitAnswer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        options.forEach { option ->
            AnswerOptionButton(
                answer = option,
                correctAnswer = "",
                selectedAnswer = selectedAnswer,
                onClick = { onAnswerSelected(option) }
            )
        }

        Button(
            onClick = onSubmitAnswer,
            enabled = selectedAnswer != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit Answer")
        }
    }
}
