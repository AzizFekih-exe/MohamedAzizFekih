package com.example.mohamedazizfekih.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mohamedazizfekih.R

// Based on Lab 3.2 - Images and Lab 9.3 - Accessibility.
@Composable
fun QuestionImage(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    height: Dp = 220.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .semantics {
                // Based on Lab 9.3 - Accessibility: screen readers get a useful image label.
                contentDescription = "Question image placeholder for Tunisian heritage landmark"
            },
        contentAlignment = Alignment.Center
    ) {
        // Based on Lab 3.2 - Images: painterResource loads drawable resources.
        // TODO: Replace placeholder references with real image drawables such as kairouan_mosque.webp.
        if (imageRes == R.drawable.placeholder) {
            Text(
                text = "Image here",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        } else {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}
