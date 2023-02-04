package com.example.tinkoff_ronzhin_yaroslav_2023.repository

import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.network.FilmApi

interface FilmsListRepository {
    suspend fun getFilmsList(): List<MyFilm>
}

class DefaultFilmsRepository: FilmsListRepository {
    override suspend fun getFilmsList(): List<MyFilm> {
        val listFilmsAsync = FilmApi.retrofitServices.getListOfFilmsAsync()
        val filmsList = listFilmsAsync.films
        return filmsList.map { getFilmById(it.filmId) }
    }

    private suspend fun getFilmById(id: Int): MyFilm {
        val film: MyFilm
        val filmAsync = FilmApi.retrofitServices.getFilmByIdAsync(id.toString())
        film = MyFilm(
            nameRu = filmAsync.nameRu,
            genres = filmAsync.genres.map { it.genre },
            countries = filmAsync.countries.map { it.country },
            description = filmAsync.description,
            year = filmAsync.year.toString(),
            posterUrl = filmAsync.posterUrl,
            filmId = filmAsync.kinopoiskId
        )
        return film
    }
}