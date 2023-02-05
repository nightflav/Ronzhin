package com.example.tinkoff_ronzhin_yaroslav_2023.model

import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.FilmScreens

data class FilmUiState(
    val films: List<MyFilm> = listOf(),
    val currHorFilm: Int = 0,
    val favouriteFilms: List<MyFilm> = listOf(),
    val currentScreen: String = FilmScreens.Popular.screenId
)