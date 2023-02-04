package com.example.tinkoff_ronzhin_yaroslav_2023.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tinkoff_ronzhin_yaroslav_2023.data.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StateUi())
    val uiState: StateFlow<StateUi> = _uiState.asStateFlow()

    fun loadFilms() {
        _uiState.update { currentState ->
            currentState.copy(
                films = listOf()
            )
        }
    }

    fun getFilmById(id: Int): Film {
        val film: Film = Film(
            nameRu = "123",
            country = "Rus",
            posterUrl = "https://dexterfan.me/wp-content/uploads/2021/10/logo.png",
            year = "2020",
            description = "shit shit shit",
            genre = "Comedy",
            filmId = 12
        );
        return film
    }
}
