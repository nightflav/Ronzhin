package com.example.tinkoff_ronzhin_yaroslav_2023.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tinkoff_ronzhin_yaroslav_2023.model.AppViewModel
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.AnimatedSplashScreen
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.DetailScreen
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.MainScreen

@Composable
fun MainNav(
    viewModel: AppViewModel = viewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Splash.screenId) {
        composable(
            Screens.Splash.screenId
        ) {
            AnimatedSplashScreen(navController)
        }
        composable(
            Screens.Main.screenId
        ) {
            MainScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            Screens.Details.screenId + "/{filmId}",
            arguments = listOf(navArgument(name = "filmId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("filmId")?.let {
                DetailScreen(
                    navController = navController,
                    filmId = it,
                    viewModel = viewModel
                )
            }
        }
    }
}