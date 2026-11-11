package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel

data class DashboardCard(val title: String, val subtitle: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboardScreen(
    viewModel: MainViewModel,
    onNavigate: (String) -> Unit
) {
    val isListening by viewModel.isListening.collectAsState()
    val currentPreset by viewModel.currentPreset.collectAsState()
    val bassBoost by viewModel.bassBoostStrength.collectAsState()

    val cards = listOf(
        DashboardCard("Wake Word", "Hey Buddy / Alexa", Icons.Default.Mic, "wake_word"),
        DashboardCard("Custom Commands", "Voice Shortcuts", Icons.Default.List, "custom_commands"),
        DashboardCard("Active Player", "MediaSession Detected", Icons.Default.MusicNote, "active_player"),
        DashboardCard("Music Controls", "Play, Pause, Volume", Icons.Default.PlayArrow, "music_controls"),
        DashboardCard("10-Band EQ", "Professional Equalizer", Icons.Default.Tune, "equalizer"),
        DashboardCard("Bass Boost", "Intensity: $bassBoost", Icons.Default.Speaker, "bass_boost"),
        DashboardCard("3D Virtualizer", "Surround Sound", Icons.Default.SurroundSound, "virtualizer"),
        DashboardCard("Reverb & Loudness", "Room Effects", Icons.Default.GraphicEq, "reverb_loudness"),
        DashboardCard("Circular Visualizer", "Neon Audio Spectrum", Icons.Default.Circle, "visualizer"),
        DashboardCard("Audio Presets", "Current: $currentPreset", Icons.Default.LibraryMusic, "presets"),
        DashboardCard("Bluetooth Profiles", "Device-wise EQ", Icons.Default.Bluetooth, "bluetooth"),
        DashboardCard("Lock Screen", "Media Controls", Icons.Default.Lock, "lock_screen"),
        DashboardCard("Speech Recognition", "Offline / Hybrid", Icons.Default.RecordVoiceOver, "speech_settings"),
        DashboardCard("Battery & Service", "Low Power Mode", Icons.Default.BatteryChargingFull, "battery"),
        DashboardCard("Permissions", "Audio & Bluetooth", Icons.Default.Security, "permissions"),
        DashboardCard("Diagnostics", "Compatibility Audit", Icons.Default.Build, "diagnostics")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Universal Music Voice Controller", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Status Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isListening) "Listening Active" else "Listening Paused",
                            color = if (isListening) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Say 'Hey Buddy' or 'Alexa' to command",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp
                        )
                    }
                    Switch(
                        checked = isListening,
                        onCheckedChange = { viewModel.toggleVoiceService(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grid of Features
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(cards) { card ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onNavigate(card.route) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = card.icon,
                                contentDescription = card.title,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Column {
                                Text(
                                    text = card.title,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = card.subtitle,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
