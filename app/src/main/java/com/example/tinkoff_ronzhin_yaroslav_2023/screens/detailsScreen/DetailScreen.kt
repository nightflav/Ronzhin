package com.example.tinkoff_ronzhin_yaroslav_2023.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tinkoff_ronzhin_yaroslav_2023.R
import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.model.DetailsUiState
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.detailsScreen.DetailsScreenViewModel
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.extraScreens.ErrorScreen
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.extraScreens.LoadingScreen

@Composable
fun DetailScreen(
    navController: NavController,
    filmId: Int,
    viewModel: DetailsScreenViewModel = viewModel()
) {
    viewModel.setFilmWithId(filmId)
    val uiState by viewModel.uiState.collectAsState()
    val orientation = LocalConfiguration.current.orientation
    when (viewModel.detailUiState) {
        is DetailsUiState.Success -> FilmDetails(
            film = uiState.film,
            orientation = orientation,
            navController = navController
        )
        is DetailsUiState.Loading -> LoadingScreen()
        is DetailsUiState.Error -> ErrorScreen(retry = { viewModel.setFilmWithId(filmId) })
    }
}

@Composable
private fun FilmDetails(film: MyFilm, orientation: Int, navController: NavController) {
    val scroll = rememberScrollState(0)
    Scaffold {
        Card(
            modifier = Modifier.padding(it)
        ) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    AsyncImage(
                        model = film.posterUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Info(
                        film = film,
                        modifier = Modifier.verticalScroll(scroll)
                    )
                }
            else Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = film.posterUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                )
                Info(
                    film = film,
                    modifier = Modifier.verticalScroll(scroll)
                )
            }
        }
        TopAppBar(
            title = { Text("") },
            backgroundColor = Color.Transparent.copy(alpha = 0f),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, "Open/Close menu", tint = Color.Blue)
                }
            },
            elevation = 0.dp
        )
    }
}

@Composable
private fun Info(
    modifier: Modifier = Modifier,
    film: MyFilm
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = film.nameRu,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = Color.DarkGray
        )
        Text(
            text = "${stringResource(id = R.string.release_year)} ${film.year}",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            color = Color.DarkGray
        )
        Text(
            text = "${stringResource(id = R.string.genres)}: ${film.genres.joinToString(", ")}",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${stringResource(id = R.string.countries)}: ${film.countries.joinToString(", ")}",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.DarkGray
        )
        Text(
            text = film.description,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(4.dp),
            lineHeight = 14.sp,
            color = Color.DarkGray
        )
    }
}