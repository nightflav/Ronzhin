package com.example.tinkoff_ronzhin_yaroslav_2023.data

data class MyFilm(
    val filmId: Int = -1,
    val nameRu: String = "",
    val posterUrl: String = "",
    val year: String = "",
    val description: String = "",
    val genres: List<String> = emptyList(),
    val countries: List<String> = emptyList()
)