package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicControlsScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    var volume by remember { mutableStateOf(50f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Universal Music Controls") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { viewModel.musicController.dispatchPrevious() }, modifier = Modifier.size(64.dp)) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = "Previous", modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.musicController.dispatchPlay() }, modifier = Modifier.size(64.dp)) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.musicController.dispatchPause() }, modifier = Modifier.size(64.dp)) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause", modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.musicController.dispatchNext() }, modifier = Modifier.size(64.dp)) {
                    Icon(Icons.Default.SkipNext, contentDescription = "Next", modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
            Text("System Music Volume: ${volume.toInt()}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Slider(
                value = volume,
                onValueChange = {
                    volume = it
                    viewModel.musicController.setVolume(it.toInt())
                },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
