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
fun BassBoostScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val bassBoost by viewModel.bassBoostStrength.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bass Boost Enhancement") },
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
            Text("Bass Boost Intensity: $bassBoost", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Slider(
                value = bassBoost.toFloat(),
                onValueChange = { viewModel.updateBassBoost(it.toInt()) },
                valueRange = 0f..1000f,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.updateBassBoost(0) }) { Text("Off") }
                Button(onClick = { viewModel.updateBassBoost(500) }) { Text("Normal") }
                Button(onClick = { viewModel.updateBassBoost(1000) }) { Text("Deep Bass") }
            }
        }
    }
}
