package com.digiwizkid.islandhopper.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.digiwizkid.islandhopper.data.models.Island
import com.digiwizkid.islandhopper.ui.theme.CorrectGreen

@Composable
internal fun IslandButton(
    island: Island,
    color: Color,
    isShaking: Boolean,
    isCorrectAnswer: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isShaking) {
        if (isShaking) {
            scale.animateTo(0.9f, tween(80))
            scale.animateTo(1.05f, tween(80))
            scale.animateTo(0.95f, tween(80))
            scale.animateTo(1f, tween(80))
        }
    }

    LaunchedEffect(isCorrectAnswer) {
        if (isCorrectAnswer) {
            scale.animateTo(1.2f, tween(150))
            scale.animateTo(1f, tween(150))
        }
    }

    val label = island.label
    val (buttonSize, fontSize) = when {
        label.length <= 6 -> Pair(110.dp, 20.sp)
        label.length <= 10 -> Pair(115.dp, 16.sp)
        label.length <= 16 -> Pair(120.dp, 13.sp)
        else -> Pair(125.dp, 10.5.sp)
    }

    Button(
        onClick = onClick,
        enabled = island.isEnabled,
        modifier = modifier
            .size(buttonSize)
            .scale(scale.value),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isCorrectAnswer) CorrectGreen else color
        ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            lineHeight = (fontSize.value + 2).sp
        )
    }
}
