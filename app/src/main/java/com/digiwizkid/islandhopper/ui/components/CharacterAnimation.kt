package com.digiwizkid.islandhopper.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CharacterAnimation(
    position: Int,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Face,
        contentDescription = "Player character",
        modifier = modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.tertiary
    )
}
