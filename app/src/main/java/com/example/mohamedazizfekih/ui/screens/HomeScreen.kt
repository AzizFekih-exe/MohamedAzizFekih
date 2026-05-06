package com.example.mohamedazizfekih.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Based on Lab 3.1 - Column/Text/Row and Lab 11.1 - Navigation Compose.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    siteCount: Int,
    masteryPercent: Int,
    streakCount: Int,
    onSettingsClick: () -> Unit,
    onStartClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Tunisia Heritage Quest") },
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
        HomeContent(
            siteCount = siteCount,
            masteryPercent = masteryPercent,
            streakCount = streakCount,
            onStartClick = onStartClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

// Based on Lab 12.1 and 12.2 - responsive design for phones and wider screens.
@Composable
private fun HomeContent(
    siteCount: Int,
    masteryPercent: Int,
    streakCount: Int,
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val statColumns = if (screenWidth < 600) 3 else 3

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tunisia",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Heritage Quest",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 18.dp, bottom = 36.dp)
                .fillMaxWidth(0.28f),
            color = MaterialTheme.colorScheme.primary
        )

        // Based on Lab 8.3 - Grid: shows the three progress numbers in equal columns.
        LazyVerticalGrid(
            columns = GridCells.Fixed(statColumns),
            modifier = Modifier
                .fillMaxWidth()
                .height(138.dp),
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(homeStats(siteCount, masteryPercent, streakCount)) { stat ->
                StatCard(stat = stat)
            }
        }

        Button(
            onClick = onStartClick,
            modifier = Modifier
                .padding(top = 46.dp)
                .fillMaxWidth()
                .semantics { contentDescription = "Start quiz" }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null
                )
                Text(text = "Start Quiz")
            }
        }
    }
}

// Based on Lab 7.1 - Data class: one small object stores each stat item.
private data class HomeStat(
    val title: String,
    val value: String,
    val icon: ImageVector
)

// Based on Lab 7.2 - Collections: returns the list shown by the grid.
private fun homeStats(
    siteCount: Int,
    masteryPercent: Int,
    streakCount: Int
): List<HomeStat> {
    return listOf(
        HomeStat(title = "Sites", value = siteCount.toString(), icon = Icons.Filled.AccountBalance),
        HomeStat(title = "Mastery", value = "$masteryPercent%", icon = Icons.Filled.Star),
        HomeStat(title = "Streak", value = streakCount.toString(), icon = Icons.AutoMirrored.Filled.TrendingUp)
    )
}

// Based on Lab 3.1 - Column/Text and Lab 9.1 - Material Theme.
@Composable
private fun StatCard(stat: HomeStat) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = stat.icon,
                contentDescription = "${stat.title} icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = stat.value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stat.title,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
