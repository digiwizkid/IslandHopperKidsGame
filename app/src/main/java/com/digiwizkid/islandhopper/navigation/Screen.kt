package com.digiwizkid.islandhopper.navigation

internal sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Game : Screen("game/{mode}") {
        fun createRoute(mode: String) = "game/$mode"
    }
    data object TimerGame : Screen("game_timer/{mode}") {
        fun createRoute(mode: String) = "game_timer/$mode"
    }
    data object Result : Screen("result/{score}") {
        fun createRoute(score: Int) = "result/$score"
    }
}
