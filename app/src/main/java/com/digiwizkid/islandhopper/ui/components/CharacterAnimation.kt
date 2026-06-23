package com.digiwizkid.islandhopper.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
internal fun CharacterAnimation(
    position: Int,
    modifier: Modifier = Modifier
) {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    val targetX = (position % 3 - 1) * 80f
    val targetY = (position / 3) * -60f

    LaunchedEffect(position) {
        offsetX.animateTo(targetX, animationSpec = tween(500))
        offsetY.animateTo(targetY - 20f, animationSpec = tween(250))
        offsetY.animateTo(targetY, animationSpec = tween(250))
    }

    Icon(
        imageVector = Icons.Default.Face,
        contentDescription = "Player character",
        modifier = modifier
            .size(64.dp)
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) },
        tint = MaterialTheme.colorScheme.tertiary
    )
}
