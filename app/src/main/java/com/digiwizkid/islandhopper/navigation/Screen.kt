package com.digiwizkid.islandhopper.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Game : Screen("game/{mode}") {
        fun createRoute(mode: String) = "game/$mode"
    }
    data object Result : Screen("result/{score}") {
        fun createRoute(score: Int) = "result/$score"
    }
}
