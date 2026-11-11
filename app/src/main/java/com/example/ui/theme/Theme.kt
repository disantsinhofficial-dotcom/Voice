package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkAmoledColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = PitchBlack,
    secondary = NeonPurple,
    onSecondary = TextPrimary,
    tertiary = NeonEmerald,
    background = PitchBlack,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkAmoledColorScheme,
        typography = Typography,
        content = content
    )
}
