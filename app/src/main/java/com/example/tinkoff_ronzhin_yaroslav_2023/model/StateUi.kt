package com.example.tinkoff_ronzhin_yaroslav_2023.model

import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm

data class FilmUiState(
    var films: List<MyFilm> = listOf(),
    val currHorFilm: Int = 0
)