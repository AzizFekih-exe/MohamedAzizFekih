package com.example.mohamedazizfekih.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

// Based on Lab 9.2 - Simple Animation: manually fades and slides a screen into view.
@Composable
fun ScreenEnterAnimation(
    content: @Composable () -> Unit
) {
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        progress = 0f
        for (step in 1..12) {
            progress = step / 12f
            delay(12)
        }
    }

    Box(
        modifier = Modifier.graphicsLayer {
            alpha = progress
            translationX = (1f - progress) * 72f
            scaleX = 0.99f + (progress * 0.01f)
            scaleY = 0.99f + (progress * 0.01f)
        }
    ) {
        content()
    }
}
