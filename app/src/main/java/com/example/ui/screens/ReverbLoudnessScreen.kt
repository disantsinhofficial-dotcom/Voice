package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReverbLoudnessScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val reverb by viewModel.reverbPreset.collectAsState()
    val loudness by viewModel.loudnessGain.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reverb & Loudness Enhancer") },
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
                .padding(24.dp)
        ) {
            Text("Reverb Preset: $reverb", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { viewModel.updateReverb(0) }) { Text("None") }
                Button(onClick = { viewModel.updateReverb(1) }) { Text("Small") }
                Button(onClick = { viewModel.updateReverb(2) }) { Text("Large") }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text("Loudness Gain: $loudness", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = loudness.toFloat(),
                onValueChange = { viewModel.updateLoudness(it.toInt()) },
                valueRange = 0f..1000f,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
