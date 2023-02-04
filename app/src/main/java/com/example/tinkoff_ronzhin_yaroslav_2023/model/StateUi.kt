package com.example.tinkoff_ronzhin_yaroslav_2023.model

import com.example.tinkoff_ronzhin_yaroslav_2023.data.Film

data class StateUi(
    var films: List<Film> = listOf(),
    val currentFilmId: Int = 0
)