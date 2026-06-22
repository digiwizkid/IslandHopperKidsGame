package com.digiwizkid.islandhopper.ui.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digiwizkid.islandhopper.data.models.GameMode
import com.digiwizkid.islandhopper.ui.components.CharacterAnimation
import com.digiwizkid.islandhopper.ui.components.IslandButton
import com.digiwizkid.islandhopper.ui.components.QuestionPrompt
import com.digiwizkid.islandhopper.ui.components.ScoreDisplay

@Composable
fun GameScreen(
    mode: GameMode,
    onGameOver: (Int) -> Unit,
    viewModel: GameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(mode) {
        viewModel.startGame(mode)
    }

    LaunchedEffect(uiState.isGameOver) {
        if (uiState.isGameOver) {
            onGameOver(uiState.score)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreDisplay(score = uiState.score)

        CharacterAnimation(position = uiState.characterPosition)

        QuestionPrompt(text = uiState.questionPrompt)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.activeIslands.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    row.forEach { island ->
                        IslandButton(
                            island = island,
                            isShaking = uiState.triggerShakeId == island.id,
                            onClick = { viewModel.selectIsland(island) },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
