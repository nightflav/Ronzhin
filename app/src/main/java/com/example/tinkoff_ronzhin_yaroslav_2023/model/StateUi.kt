package com.example.tinkoff_ronzhin_yaroslav_2023.model

import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm

data class StateUi(
    var films: List<MyFilm> = listOf(),
    val currentFilmId: Int = 0
)