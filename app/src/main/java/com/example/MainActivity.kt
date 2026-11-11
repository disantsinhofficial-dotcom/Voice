package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "dashboard",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("dashboard") {
                        HomeDashboardScreen(viewModel = viewModel, onNavigate = { route -> navController.navigate(route) })
                    }
                    composable("wake_word") {
                        WakeWordSettingsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("custom_commands") {
                        CustomCommandsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("active_player") {
                        ActiveMusicPlayerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("music_controls") {
                        MusicControlsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("equalizer") {
                        TenBandEqualizerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("bass_boost") {
                        BassBoostScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("virtualizer") {
                        VirtualizerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("reverb_loudness") {
                        ReverbLoudnessScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("visualizer") {
                        CircularVisualizerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("presets") {
                        AudioPresetsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("bluetooth") {
                        BluetoothProfilesScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("lock_screen") {
                        LockScreenControlsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("speech_settings") {
                        SpeechRecognitionSettingsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("battery") {
                        BatteryOptimizationScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("permissions") {
                        PermissionsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("diagnostics") {
                        DiagnosticsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
