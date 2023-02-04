package com.example.tinkoff_ronzhin_yaroslav_2023.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.network.FilmApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StateUi())
    val uiState: StateFlow<StateUi> = _uiState.asStateFlow()

    fun loadFilms() {
        viewModelScope.launch {
            val listFilmsAsync = FilmApi.retrofitServices.getListOfFilmsAsync()

            val filmsList = listFilmsAsync.films

            val extendedFilmsList = filmsList.map { getFilmById(it.filmId) }

            _uiState.update { currentState ->
                currentState.copy(
                    films = extendedFilmsList
                )
            }
        }
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

    fun getFilmWithId(id: Int): MyFilm {
        for(film in _uiState.value.films)
            if(id == film.filmId)
                return film
        return _uiState.value.films[0]
    }
}