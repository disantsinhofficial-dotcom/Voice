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
fun VirtualizerScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val virtualizer by viewModel.virtualizerStrength.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("3D Surround Virtualizer") },
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
            Text("3D Audio Strength: $virtualizer", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Slider(
                value = virtualizer.toFloat(),
                onValueChange = { viewModel.updateVirtualizer(it.toInt()) },
                valueRange = 0f..1000f,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
