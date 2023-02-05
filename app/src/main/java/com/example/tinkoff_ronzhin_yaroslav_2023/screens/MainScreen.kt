package com.example.tinkoff_ronzhin_yaroslav_2023.screens

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tinkoff_ronzhin_yaroslav_2023.R
import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.model.AppViewModel
import com.example.tinkoff_ronzhin_yaroslav_2023.model.FilmListUiState
import com.example.tinkoff_ronzhin_yaroslav_2023.navigation.Screens

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: AppViewModel
) {
    viewModel.loadFilms()
    when (val filmUiState = viewModel.filmUiState) {
        is FilmListUiState.Success -> ListOfFilms(
            films = filmUiState.films,
            navigateToFilmDetails = { film ->
                navController.navigate(route = Screens.Details.screenId + "/${film.filmId}")
            }, viewModel)
        is FilmListUiState.Loading -> LoadingScreen()
        is FilmListUiState.Error -> ErrorScreen(viewModel = viewModel)
    }
}

@Composable
private fun ListOfFilms(films: List<MyFilm>, navigateToFilmDetails: (MyFilm) -> Unit, viewModel: AppViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()

    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT)
        LazyColumn(
            modifier = Modifier.padding(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = scrollState
        ) {
            itemsIndexed(films) { _, film ->
                FilmItemVertical(
                    film = film,
                    navigateToFilmDetails = navigateToFilmDetails
                )
            }
        }
    else {
        Row{
            Box(
                modifier = Modifier.weight(1f)
            ){
                LazyColumn(
                    modifier = Modifier.padding(all = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = scrollState
                ) {
                    itemsIndexed(films) { _, film ->
                        FilmItemVertical(
                            film = film,
                            navigateToFilmDetails = { viewModel.renewCurrHorFilm(films.indexOf(film)) }
                        )
                    }
                }
            }
            Text(
                text = films[uiState.currHorFilm].description,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun FilmItemVertical(
    film: MyFilm,
    navigateToFilmDetails: (MyFilm) -> Unit,
    brush: Brush = Brush.linearGradient(
        colors = listOf(
            Color.White,
            Color.White
        )
    )
) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clickable { navigateToFilmDetails(film) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(brush),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = film.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .requiredSizeIn(
                        maxHeight = 128.dp,
                        maxWidth = 128.dp
                    )
                    .background(brush),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(brush),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = film.nameRu,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    Modifier
                        .height(4.dp)
                        .background(brush)
                )
                Text(
                    text = film.year,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
private fun FilmItemHorizontal(
    film: MyFilm,
    navigateToFilmDetails: (MyFilm) -> Unit = {},
    brush: Brush = Brush.linearGradient(
        colors = listOf(
            Color.White,
            Color.White
        )
    )
) {
    val scroll = rememberScrollState(0)
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(192.dp)
            .clickable { navigateToFilmDetails(film) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .background(brush)
        ) {
            AsyncImage(
                model = film.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f, true)
                    .background(brush),
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(brush),
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


@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    val shimmerEffectColors = listOf(
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.8f),
        Color.LightGray.copy(alpha = 0.2f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        shimmerEffectColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )

    val tmpMyFilm = MyFilm(
        nameRu = "",
        description = "",
        filmId = -1,
        year = "",
        genres = emptyList(),
        countries = emptyList(),
        posterUrl = ""
    )

    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
        Column(modifier = Modifier.padding(16.dp)) {
            for (i in 0..9) {
                FilmItemVertical(film = tmpMyFilm, navigateToFilmDetails = { }, brush)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    else
        Column {
            for (i in 0..9) {
                FilmItemHorizontal(film = tmpMyFilm, navigateToFilmDetails = { }, brush)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
}

@Composable
private fun ErrorScreen(modifier: Modifier = Modifier, viewModel: AppViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.internet_error))
            Button(onClick = { viewModel.loadFilms() }) {
                Text(text = stringResource(id = R.string.try_again_error_screen))
            }
        }
    }
}