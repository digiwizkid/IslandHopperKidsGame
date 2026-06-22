package com.digiwizkid.islandhopper.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.digiwizkid.islandhopper.data.models.Island
import com.digiwizkid.islandhopper.ui.theme.IslandGreen

@Composable
fun IslandButton(
    island: Island,
    isShaking: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isShaking) {
        if (isShaking) {
            scale.animateTo(
                targetValue = 0.9f,
                animationSpec = tween(100)
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(100)
            )
        }
    }

    Button(
        onClick = onClick,
        enabled = island.isEnabled,
        modifier = modifier
            .size(120.dp)
            .scale(scale.value),
        colors = ButtonDefaults.buttonColors(
            containerColor = IslandGreen
        )
    ) {
        Text(text = island.label)
    }
}
