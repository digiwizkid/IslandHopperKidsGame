package com.digiwizkid.islandhopper.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digiwizkid.islandhopper.ui.theme.IslandGreen
import com.digiwizkid.islandhopper.ui.theme.OceanBlue
import com.digiwizkid.islandhopper.ui.theme.CoralRed

@Composable
fun HomeScreen(
    onModeSelected: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Island Hopper",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Pick a game mode to start learning!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        viewModel.availableModes.forEach { modeOption ->
            Button(
                onClick = { onModeSelected(modeOption.mode.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (modeOption.mode) {
                        com.digiwizkid.islandhopper.data.models.GameMode.SHAPES -> CoralRed
                        com.digiwizkid.islandhopper.data.models.GameMode.LETTERS -> IslandGreen
                        com.digiwizkid.islandhopper.data.models.GameMode.NUMBERS -> OceanBlue
                    }
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = modeOption.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = modeOption.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
