package com.example.tinkoff_ronzhin_yaroslav_2023.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.repository.DefaultFilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FilmUiState())
    var filmUiState: FilmListUiState by mutableStateOf(FilmListUiState.Loading)
        private set

    private val repository = DefaultFilmsRepository()

    fun abs() {
        filmUiState
    }

    fun loadFilms() {
        viewModelScope.launch {
            filmUiState = try {
                val films = repository.getFilmsList()
                _uiState.value.films = films
                FilmListUiState.Success(films)
            } catch (e: IOException) {
                FilmListUiState.Error
            }
        }
    }

    fun getFilmWithId(id: Int): MyFilm {
        for(film in _uiState.value.films)
            if(id == film.filmId)
                return film
        return _uiState.value.films[0]
    }
}