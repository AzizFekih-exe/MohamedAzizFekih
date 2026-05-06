package com.example.mohamedazizfekih.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mohamedazizfekih.data.HeritageRepository
import com.example.mohamedazizfekih.model.HeritageCategory

@OptIn(ExperimentalMaterial3Api::class)
// Based on Lab 3.1 - Compose UI, Lab 3.2 - Images, and Lab 8.3 - Grid.
@Composable
fun CategoryScreen(
    isDarkTheme: Boolean,
    onThemeChange: () -> Unit,
    onSettingsClick: () -> Unit,
    onCategorySelected: (String) -> Unit
) {
    // Based on Lab 6.1 - State: stores which unavailable category was clicked.
    var comingSoonCategory by remember {
        mutableStateOf<HeritageCategory?>(null)
    }

    // Based on Lab 9.1 - Material Theme: Material components read colors/type from the app theme.
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Tunisia Heritage Quest")
                },
                actions = {
                    // Based on Lab 5.1 - Buttons: simple button to change theme.
                    Button(onClick = onThemeChange) {
                        val text = if (isDarkTheme) "Light" else "Dark"
                        Text(text = text)
                    }
                    // Based on Lab 9.2 - Material icons: gear icon opens settings.
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
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val columns = if (screenWidth < 600) 1 else 2

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Based on Lab 8.3 - Grid: categories are shown in a responsive grid.
            // Based on Lab 12.1 & 12.2 - Responsive design: column count adapts to screen width.
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(HeritageRepository.categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = {
                            // Based on Lab 4.1 - Conditionals: only Islamic Heritage is playable now.
                            if (category.id == HeritageRepository.ISLAMIC_HERITAGE_ID) {
                                onCategorySelected(category.id)
                            } else {
                                comingSoonCategory = category
                            }
                        }
                    )
                }
            }
        }
    }

    comingSoonCategory?.let { category ->
        // Based on Lab 9.1 - Material Theme: AlertDialog shows a simple popup.
        AlertDialog(
            onDismissRequest = { comingSoonCategory = null },
            title = { Text(text = "Coming Soon") },
            text = {
                Text(
                    text = "${category.title} will be added later. For now, please play Islamic Heritage."
                )
            },
            confirmButton = {
                Button(onClick = { comingSoonCategory = null }) {
                    Text(text = "OK")
                }
            }
        )
    }
}

// Based on Lab 3.1 - Row and Icons: returns a simple category icon.
private fun categoryIcon(categoryId: String): ImageVector {
    return when (categoryId) {
        "roman_heritage" -> Icons.Filled.AccountBalance
        "islamic_heritage" -> Icons.Filled.Museum
        "punic_pre_roman" -> Icons.Filled.Public
        "modern_heritage" -> Icons.Filled.LocationCity
        "natural_mixed_sites" -> Icons.Filled.Landscape
        else -> Icons.Filled.AddCircle
    }
}

// Based on Lab 5.3 - Clickable and Lab 9.1 - Material Theme.
@Composable
private fun CategoryCard(
    category: HeritageCategory,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        modifier = Modifier
            .fillMaxWidth()
            // Based on Lab 5.3 - Clickable: cards can navigate when tapped.
            .clickable(onClick = onClick)
            .semantics { contentDescription = "Category ${category.title}" }
    ) {
        // Based on Lab 3.1 - Text, Column, Row: Column arranges text vertically.
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                imageVector = categoryIcon(category.id),
                contentDescription = "${category.title} icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(34.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = category.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${category.questions.size} questions",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
