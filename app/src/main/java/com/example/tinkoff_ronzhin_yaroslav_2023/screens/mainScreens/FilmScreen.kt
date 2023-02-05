package com.example.tinkoff_ronzhin_yaroslav_2023.screens.mainScreens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.model.FilmListUiState
import com.example.tinkoff_ronzhin_yaroslav_2023.model.FilmUiState
import com.example.tinkoff_ronzhin_yaroslav_2023.navigation.Screens
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.FilmItemVertical
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.FilmScreens
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.extraScreens.ErrorScreen
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.extraScreens.LoadingScreen

@Composable
fun FilmScreen(
    viewModel: AppViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    when (viewModel.filmUiState) {
        is FilmListUiState.Success -> ActualFilmsScreen(
            navigateToFilmDetails = { film ->
                navController.navigate(route = Screens.Details.screenId + "/${film.filmId}")
            },
            horizontalFilmOnClick = { film -> viewModel.renewCurrHorFilm(uiState.films.indexOf(film)) },
            uiState = uiState,
            addToFavouriteFilms = { viewModel.updateFilmInFavourites(it) }
        )
        is FilmListUiState.Loading -> LoadingScreen()
        is FilmListUiState.Error ->  ErrorScreen(retry = { viewModel.loadFilms() })
    }
}

@Composable
private fun ActualFilmsScreen(
    navigateToFilmDetails: (MyFilm) -> Unit = {},
    horizontalFilmOnClick: (MyFilm) -> Unit = {},
    addToFavouriteFilms: (MyFilm) -> Unit = {},
    uiState: FilmUiState
) {
    val filmsToShow = when(uiState.currentScreen) {
        FilmScreens.Favourite.screenId -> uiState.favouriteFilms
        FilmScreens.Popular.screenId -> uiState.films
        else -> emptyList()
    }
    val scrollState = rememberLazyListState()
    val orientation = LocalConfiguration.current.orientation

    if (orientation == Configuration.ORIENTATION_PORTRAIT)
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = scrollState
        ) {
            itemsIndexed(filmsToShow) { _, film ->
                FilmItemVertical(
                    film = film,
                    navigateToFilmDetails = navigateToFilmDetails,
                    addToFavouriteFilms = addToFavouriteFilms,
                    isFavourite = film in uiState.favouriteFilms
                )
            }
        }
    else {
        Row {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(all = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = scrollState
                ) {
                    itemsIndexed(filmsToShow) { _, film ->
                        FilmItemVertical(
                            film = film,
                            navigateToFilmDetails = { horizontalFilmOnClick(it) },
                            addToFavouriteFilms = addToFavouriteFilms,
                            isFavourite = film in uiState.favouriteFilms
                        )
                    }
                }
            }
            Text(
                text = uiState.films[uiState.currHorFilm].description,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}