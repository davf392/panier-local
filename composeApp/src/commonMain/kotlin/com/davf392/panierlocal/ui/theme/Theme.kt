package com.davf392.panierlocal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Green80,
    onPrimaryContainer = Green40,

    secondary = Olive40,
    onSecondary = Color.White,
    secondaryContainer = Olive80,
    onSecondaryContainer = Olive40,

    tertiary = Yellow40,
    onTertiary = Color.Black,
    tertiaryContainer = Yellow80,
    onTertiaryContainer = Yellow40,

    background = Beige80,
    onBackground = Color(0xFF3E3E3E),
    surface = Color.White,
    onSurface = Color(0xFF3E3E3E),
)

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    onPrimary = Green40,
    primaryContainer = Green40,
    onPrimaryContainer = Green80,

    secondary = Olive80,
    onSecondary = Olive40,
    secondaryContainer = Olive40,
    onSecondaryContainer = Olive80,

    tertiary = Yellow80,
    onTertiary = Yellow40,
    tertiaryContainer = Yellow40,
    onTertiaryContainer = Yellow80,

    background = Color(0xFF1C1C1C),
    onBackground = Color(0xFFECECEC),
    surface = Color(0xFF2C2C2C),
    onSurface = Color(0xFFECECEC),
)

@Composable
fun PanierLocalTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}