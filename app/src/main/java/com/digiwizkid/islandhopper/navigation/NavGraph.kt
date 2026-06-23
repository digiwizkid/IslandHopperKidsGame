package com.digiwizkid.islandhopper.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.digiwizkid.islandhopper.data.models.GameMode
import com.digiwizkid.islandhopper.ui.screens.game.GameScreen
import com.digiwizkid.islandhopper.ui.screens.home.HomeScreen
import com.digiwizkid.islandhopper.ui.screens.result.ResultScreen

@Composable
internal fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) }
        ) {
            HomeScreen(
                onModeSelected = { mode ->
                    navController.navigate(Screen.Game.createRoute(mode))
                },
                onTimerModeSelected = { mode ->
                    navController.navigate(Screen.TimerGame.createRoute(mode))
                }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType }
            ),
            enterTransition = {
                fadeIn(tween(300))
            }
        ) { backStackEntry ->
            val modeName = backStackEntry.arguments?.getString("mode") ?: return@composable
            val mode = try { GameMode.valueOf(modeName) } catch (_: IllegalArgumentException) { return@composable }
            GameScreen(
                mode = mode,
                onGameOver = { score ->
                    navController.navigate(Screen.Result.createRoute(score)) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.TimerGame.route,
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType }
            ),
            enterTransition = {
                fadeIn(tween(300))
            }
        ) { backStackEntry ->
            val modeName = backStackEntry.arguments?.getString("mode") ?: return@composable
            val mode = try { GameMode.valueOf(modeName) } catch (_: IllegalArgumentException) { return@composable }
            GameScreen(
                mode = mode,
                timerMode = true,
                onGameOver = { score ->
                    navController.navigate(Screen.Result.createRoute(score)) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType }
            ),
            enterTransition = {
                fadeIn(tween(300))
            }
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            ResultScreen(
                score = score,
                onPlayAgain = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
