package com.example.tinkoff_ronzhin_yaroslav_2023.screens.detailsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoff_ronzhin_yaroslav_2023.model.DetailsScreenUiState
import com.example.tinkoff_ronzhin_yaroslav_2023.model.DetailsUiState
import com.example.tinkoff_ronzhin_yaroslav_2023.repository.DefaultFilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class DetailsScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(DetailsScreenUiState())
    val uiState: StateFlow<DetailsScreenUiState> = _uiState.asStateFlow()
    var detailUiState: DetailsUiState by mutableStateOf(DetailsUiState.Loading)
        private set

    private val repository = DefaultFilmsRepository()

    fun setFilmWithId(id: Int) {
        viewModelScope.launch {
            detailUiState = try {
                val film = repository.getFilmById(id)
                _uiState.update { currState ->
                    currState.copy(
                        film = film
                    )
                }
                DetailsUiState.Success(film)
            } catch (e: IOException) {
                DetailsUiState.Error
            }
        }
    }
}