package com.digiwizkid.islandhopper.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
internal fun ParticleEffect(
    show: Boolean,
    modifier: Modifier = Modifier
) {
    if (!show) return

    val particles = listOf("⭐", "🌟", "✨", "🎉", "💫")

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        particles.forEach { emoji ->
            val infiniteTransition = rememberInfiniteTransition(label = "particle_$emoji")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(800, easing = LinearEasing)
                ),
                label = "alpha_$emoji"
            )

            Text(
                text = emoji,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}
