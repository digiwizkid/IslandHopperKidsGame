package com.digiwizkid.islandhopper.ui.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digiwizkid.islandhopper.data.models.Difficulty
import com.digiwizkid.islandhopper.data.models.GameMode
import androidx.compose.foundation.background
import com.digiwizkid.islandhopper.ui.components.CharacterAnimation
import com.digiwizkid.islandhopper.ui.components.IslandButton
import com.digiwizkid.islandhopper.ui.components.ParticleEffect
import com.digiwizkid.islandhopper.ui.components.QuestionPrompt
import com.digiwizkid.islandhopper.ui.components.ScoreDisplay
import com.digiwizkid.islandhopper.ui.theme.AnswerColors
import com.digiwizkid.islandhopper.ui.theme.White

@Composable
internal fun GameScreen(
    mode: GameMode,
    timerMode: Boolean = false,
    onGameOver: (Int) -> Unit,
    viewModel: GameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(mode) {
        viewModel.startGame(mode, timerMode)
    }

    LaunchedEffect(uiState.isGameOver) {
        if (uiState.isGameOver) {
            onGameOver(uiState.score)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (uiState.isGameOver) return@Column

        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.showLevelUp) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🎉",
                            fontSize = 64.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = uiState.levelUpMessage,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ScoreDisplay(
                            score = uiState.score,
                            streak = uiState.streak,
                            timeRemaining = if (uiState.isTimerMode) uiState.timeRemaining else null,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(onClick = { viewModel.toggleMusic() }) {
                            Icon(
                                imageVector = if (uiState.isMusicOn) Icons.Default.MusicNote
                                              else Icons.Default.MusicOff,
                                contentDescription = if (uiState.isMusicOn) "Mute music"
                                                     else "Unmute music",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    CharacterAnimation(position = uiState.characterPosition)

                    if (uiState.difficulty != Difficulty.STARTER) {
                        Text(
                            text = "Level: ${uiState.difficulty.displayName}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    QuestionPrompt(
                        text = uiState.questionPrompt,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        uiState.activeIslands.chunked(2).forEach { row ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                row.forEach { island ->
                                    IslandButton(
                                        island = island,
                                        color = AnswerColors[(island.id - 1) % AnswerColors.size],
                                        isShaking = uiState.triggerShakeId == island.id,
                                        isCorrectAnswer = uiState.lastAnswerCorrect == true &&
                                            island.id == uiState.activeIslands.firstOrNull { it.isCorrect }?.id,
                                        onClick = { viewModel.selectIsland(island) }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            ParticleEffect(show = uiState.showParticles)
        }
    }
}
