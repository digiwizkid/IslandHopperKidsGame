package com.digiwizkid.islandhopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.digiwizkid.islandhopper.navigation.NavGraph
import com.digiwizkid.islandhopper.ui.theme.IslandHopperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IslandHopperTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
