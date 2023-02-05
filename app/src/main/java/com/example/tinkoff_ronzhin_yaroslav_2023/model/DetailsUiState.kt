package com.example.tinkoff_ronzhin_yaroslav_2023.model

import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm

sealed class DetailsUiState {
    data class Success (val film: MyFilm) : DetailsUiState()
    object Loading : DetailsUiState()
    object Error : DetailsUiState()
}