package com.example.tinkoff_ronzhin_yaroslav_2023.screens

import android.content.res.Configuration
import android.view.OrientationEventListener
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tinkoff_ronzhin_yaroslav_2023.data.Film
import com.example.tinkoff_ronzhin_yaroslav_2023.model.AppViewModel
import com.example.tinkoff_ronzhin_yaroslav_2023.navigation.Screens
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.max

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: AppViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.loadFilms()
    val films = uiState.films
    ListOfFilms(films = films, navigateToFilmDetails = { film ->
        navController.navigate(route = Screens.Details.screenId + "/${film.filmId}")
    })
}

@Composable
private fun ListOfFilms(films: List<Film>, navigateToFilmDetails: (Film) -> Unit) {
    val orientation = LocalConfiguration.current.orientation
    LazyColumn(
        modifier = Modifier.padding(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(films) { _, film ->
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                FilmItemVertical(film = film, navigateToFilmDetails = navigateToFilmDetails)
            else
                FilmItemHorizontal(film = film, navigateToFilmDetails = navigateToFilmDetails)
        }
    }
}

@Composable
private fun FilmItemVertical(film: Film, navigateToFilmDetails: (Film) -> Unit) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clickable { navigateToFilmDetails(film) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = film.posterUrl,
                contentDescription = null,
                modifier = Modifier.requiredSizeIn(
                    maxHeight = 128.dp,
                    maxWidth = 128.dp
                ),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = film.nameRu,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = film.year,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
private fun FilmItemHorizontal(film: Film, navigateToFilmDetails: (Film) -> Unit = {}) {
    val scroll = rememberScrollState(0)
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(192.dp)
            .clickable { navigateToFilmDetails(film) }
    ) {
        Row(
            modifier = Modifier.fillMaxHeight()
        ) {
            AsyncImage(
                model = film.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = film.nameRu,
                    style = MaterialTheme.typography.h6,
                    fontSize = 24.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = film.year,
                    style = MaterialTheme.typography.h6,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = film.description,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.verticalScroll(scroll)
                )
            }
        }
    }
}