package com.example.tinkoff_ronzhin_yaroslav_2023.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmItemVertical(
    isFavourite: Boolean = false,
    film: MyFilm,
    navigateToFilmDetails: (MyFilm) -> Unit = {},
    addToFavouriteFilms: (MyFilm) -> Unit = {},
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
            .combinedClickable(
                onClick = { navigateToFilmDetails(film) },
                onLongClick = { addToFavouriteFilms(film) })
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(brush),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
                    .padding(horizontal = 16.dp)
                    .background(brush)
                    .requiredSizeIn(maxWidth = 200.dp)
                    .weight(7f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = film.nameRu,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
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
            Icon(
                imageVector = Icons.Outlined.Star,
                tint = if(isFavourite) Color.Yellow else Color.Transparent,
                contentDescription = "Favoured",
                modifier = Modifier.padding(end = 16.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun FilmItemHorizontal(
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
