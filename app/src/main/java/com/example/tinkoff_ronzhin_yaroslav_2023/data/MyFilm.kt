package com.example.tinkoff_ronzhin_yaroslav_2023.data

data class MyFilm(
    val filmId: Int,
    val nameRu: String,
    val posterUrl: String,
    val year: String,
    val description: String,
    val genres: List<String>,
    val countries: List<String>
)