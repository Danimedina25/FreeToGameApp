package com.danifitdev.freetogameapp.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.danifitdev.freetogameapp.ui.screens.GameDetailScreen
import com.danifitdev.freetogameapp.ui.screens.ListOfGamesScreen
import com.danifitdev.freetogameapp.ui.screens.SplashScreen

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object ListOfGamesScreen : Screen("list_of_games_screen")
    object GameDetailScreen: Screen("game_detail_screen/{idGame}")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.ListOfGamesScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.ListOfGamesScreen.route) {
            ListOfGamesScreen(navController)
        }
        composable(route = Screen.GameDetailScreen.route, arguments = listOf(navArgument("idGame") { type = NavType.StringType }) ){ backStackEntry ->
            val idGame = backStackEntry.arguments?.getString("idGame") ?: return@composable
            Log.d("idGame", idGame)
            GameDetailScreen(idGame.toInt(), navController)
        }
    }
}