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
fun WakeWordSettingsScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    var selectedWakeWord by remember { mutableStateOf("Hey Buddy") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wake Word Settings") },
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
                .padding(16.dp)
        ) {
            Text("Select Wake Word", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(12.dp))

            listOf("Hey Buddy", "Alexa", "Hey Music").forEach { word ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(word, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
                    RadioButton(
                        selected = selectedWakeWord == word,
                        onClick = { selectedWakeWord = word }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Two-Stage Recognition Security", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Stage 1: Continuous offline listening for wake word.\nStage 2: Voice command execution only after successful wake word detection.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
    }
}
