package com.example.tinkoff_ronzhin_yaroslav_2023.model

import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm

sealed class FilmListUiState {
    data class Success (val films: List<MyFilm>) : FilmListUiState()
    object Loading : FilmListUiState()
    object Error : FilmListUiState()
}