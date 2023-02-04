package com.example.tinkoff_ronzhin_yaroslav_2023.screens

import android.content.res.Configuration
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tinkoff_ronzhin_yaroslav_2023.data.Film
import com.example.tinkoff_ronzhin_yaroslav_2023.model.AppViewModel


@Composable
fun DetailScreen(navController: NavController, filmId: Int, viewModel: AppViewModel) {
    val film = viewModel.getFilmById(filmId)
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        FilmDetails(film = film, orientation = orientation)
}

@Composable
private fun FilmDetails(film: Film, orientation: Int) {
    Card(
        shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)
    ) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = film.posterUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                )
                Info(
                    film = film
                )
            }
        else Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = film.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,
            )
            Info(
                film = film
            )
        }
    }
}

@Composable
private fun Info(
    modifier: Modifier = Modifier,
    film: Film
) {
    val scroll = rememberScrollState(0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = film.nameRu,
            style = MaterialTheme.typography.h4,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
        Text(
            text = "Год выпуска: ${film.year}",
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = "Жанр: ${film.genre}",
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = "Страна производства: ${film.country}",
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = film.description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.verticalScroll(state = scroll)
        )
    }
}

@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun DetailScreen() {
    val film = Film(
        nameRu = "123",
        country = "Rus",
        posterUrl = "https://dexterfan.me/wp-content/uploads/2021/10/logo.png",
        year = "2020",
        description = "shit shit shit",
        genre = "Comedy",
        filmId = 12
    )
    FilmDetails(film = film, LocalConfiguration.current.orientation)
}