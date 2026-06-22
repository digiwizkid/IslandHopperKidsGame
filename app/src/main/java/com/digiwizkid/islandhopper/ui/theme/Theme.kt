package com.digiwizkid.islandhopper.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = OceanBlue,
    secondary = IslandGreen,
    tertiary = CoralRed,
    background = SkyBlue,
    surface = SandyBeach,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = DarkText,
    onSurface = DarkText
)

@Composable
fun IslandHopperTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
