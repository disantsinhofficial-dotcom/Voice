package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPurple
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun CircularVisualizerCanvas(
    isAnimating: Boolean = true,
    modifier: Modifier = Modifier
) {
    val phase by produceState(initialValue = 0f, isAnimating) {
        while (true) {
            if (isAnimating) {
                value += 0.05f
                kotlinx.coroutines.delay(16)
            } else {
                kotlinx.coroutines.delay(100)
            }
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val baseRadius = size.minDimension / 3f
        val barsCount = 64

        val brush = Brush.sweepGradient(
            colors = listOf(NeonCyan, NeonPurple, NeonCyan),
            center = Offset(centerX, centerY)
        )

        for (i in 0 until barsCount) {
            val angle = (i * 2f * Math.PI / barsCount).toFloat()
            val randomFactor = (sin(phase + i * 0.2f) + 1f) * 0.5f
            val barLength = baseRadius * 0.3f * randomFactor + (Random.nextFloat() * 10f)

            val startX = centerX + baseRadius * cos(angle)
            val startY = centerY + baseRadius * sin(angle)
            val endX = centerX + (baseRadius + barLength) * cos(angle)
            val endY = centerY + (baseRadius + barLength) * sin(angle)

            drawLine(
                brush = brush,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 6f
            )
        }

        drawCircle(
            brush = brush,
            radius = baseRadius * 0.85f,
            center = Offset(centerX, centerY),
            style = Stroke(width = 4f)
        )
    }
}
