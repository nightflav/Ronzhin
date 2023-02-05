package com.example.tinkoff_ronzhin_yaroslav_2023.screens.mainScreens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.model.FilmListUiState
import com.example.tinkoff_ronzhin_yaroslav_2023.model.FilmUiState
import com.example.tinkoff_ronzhin_yaroslav_2023.repository.DefaultFilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FilmUiState())
    val uiState: StateFlow<FilmUiState> = _uiState.asStateFlow()
    var filmUiState: FilmListUiState by mutableStateOf(FilmListUiState.Loading)
        private set

    private val repository = DefaultFilmsRepository()

    fun loadFilms() {
        if (_uiState.value.films == FilmUiState().films)
            viewModelScope.launch {
                filmUiState = try {
                    val films = repository.getFilmsList()
                    _uiState.update { currState ->
                        currState.copy(
                            films = films
                        )
                    }
                    FilmListUiState.Success(films)
                } catch (e: IOException) {
                    Log.d("MyTag", "SorryBro")
                    FilmListUiState.Error
                }
            }

        Log.d("MyTag", "That is good")
    }

    fun renewCurrHorFilm(newId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                currHorFilm = newId
            )
        }
    }

    fun updateFilmInFavourites(film: MyFilm) {
        val newList = _uiState.value.favouriteFilms.toMutableList()
        if (film in _uiState.value.favouriteFilms)
            newList.remove(film)
        else
            newList.add(film)
        _uiState.update { currState ->
            currState.copy(
                favouriteFilms = newList.toList()
            )
        }
    }

    fun changeCurrSelectedScreen(newScreen: String) {
        _uiState.update { currState ->
            currState.copy(
                currentScreen = newScreen
            )
        }
    }
}