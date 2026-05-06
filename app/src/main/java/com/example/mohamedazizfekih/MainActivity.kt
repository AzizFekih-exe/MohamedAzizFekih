package com.example.mohamedazizfekih

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mohamedazizfekih.model.AppSettings
import com.example.mohamedazizfekih.navigation.QuestNavGraph
import com.example.mohamedazizfekih.ui.theme.MohamedAzizFekihTheme

class MainActivity : ComponentActivity() {
    // Based on Lab 10.1 - Activity lifecycle: onCreate starts the Compose UI.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Based on Lab 6.1 - State: this state changes the app theme.
            var isDarkTheme by remember { mutableStateOf(false) }
            var settings by remember { mutableStateOf(AppSettings()) }

            MohamedAzizFekihTheme(darkTheme = isDarkTheme) {
                QuestNavGraph(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = !isDarkTheme },
                    settings = settings,
                    onTimerChange = { settings = settings.copy(useTimer = it) },
                    onSoundChange = { settings = settings.copy(soundEnabled = it) },
                    onHapticChange = { settings = settings.copy(hapticEnabled = it) }
                )
            }
        }
    }
}
