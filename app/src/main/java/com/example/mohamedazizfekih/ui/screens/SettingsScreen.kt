package com.example.mohamedazizfekih.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mohamedazizfekih.model.AppSettings

@OptIn(ExperimentalMaterial3Api::class)
// Based on Lab 6.1 - State: switches update app settings.
@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    settings: AppSettings,
    onThemeChange: () -> Unit,
    onTimerChange: (Boolean) -> Unit,
    onSoundChange: (Boolean) -> Unit,
    onHapticChange: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Game Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            SettingRow(
                title = "Dark Theme",
                subtitle = "Change between light and dark colors",
                checked = isDarkTheme,
                onCheckedChange = { onThemeChange() }
            )
            SettingRow(
                title = "Timer",
                subtitle = "Use 15 seconds for each question",
                checked = settings.useTimer,
                onCheckedChange = onTimerChange
            )
            SettingRow(
                title = "Sound Effects",
                subtitle = "Play a short sound when you answer",
                checked = settings.soundEnabled,
                onCheckedChange = onSoundChange
            )
            SettingRow(
                title = "Haptic Feedback",
                subtitle = "Vibrate lightly when you answer",
                checked = settings.hapticEnabled,
                onCheckedChange = onHapticChange
            )
        }
    }
}

// Based on Lab 3.1 - Row/Text and Lab 6.1 - Boolean state with Switch.
@Composable
private fun SettingRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    // Based on Lab 3.1 - Row and Text: each setting is displayed horizontally.
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        // Based on Lab 6.1 - State: Switch changes Boolean state.
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
