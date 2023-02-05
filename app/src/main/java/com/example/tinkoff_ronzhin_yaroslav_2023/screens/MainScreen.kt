package com.example.tinkoff_ronzhin_yaroslav_2023.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tinkoff_ronzhin_yaroslav_2023.navigation.Screens
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.mainScreens.AppViewModel
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.mainScreens.FilmScreen

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: AppViewModel = viewModel()
) {
    viewModel.loadFilms()
    FilmsAndBottomBar(navController = navController, viewModel)
}

@Composable
fun FilmsAndBottomBar(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    Scaffold {
        FilmScreen(navController = navController, viewModel = viewModel)
        Box {
            Column {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(it)
                )
                BottomBar(navController, viewModel)
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, viewModel: AppViewModel) {
    val screens = listOf(
        FilmScreens.Popular,
        FilmScreens.Favourite
    )

    val currentScreen = navController.currentDestination?.route

    if (currentScreen == Screens.Main.screenId) {
        Row(
            Modifier
                .background(Color.Transparent)
        ) {
            screens.forEach { screen ->
                Card(
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 16.dp)
                        .height(64.dp)
                        .clickable {
                            viewModel.changeCurrSelectedScreen(screen.screenId)
                        },
                    elevation = 8.dp,
                ) {
                    Text(
                        text = screen.title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentHeight()
                    )
                }
            }
        }
    }
}

sealed class FilmScreens(val screenId: String, val title: String) {
    object Popular : FilmScreens("popular_films", "Популярные")
    object Favourite : FilmScreens("favourite_films", "Любимые")
}