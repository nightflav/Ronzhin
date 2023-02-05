package com.example.tinkoff_ronzhin_yaroslav_2023.screens.extraScreens

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tinkoff_ronzhin_yaroslav_2023.R
import com.example.tinkoff_ronzhin_yaroslav_2023.data.MyFilm
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.FilmItemHorizontal
import com.example.tinkoff_ronzhin_yaroslav_2023.screens.FilmItemVertical


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
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
                FilmItemVertical(film = tmpMyFilm, brush = brush)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    else
        Column {
            for (i in 0..9) {
                FilmItemHorizontal(film = tmpMyFilm, brush = brush)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, retry: () -> Unit) {
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
            Button(onClick = { retry() }) {
                Text(text = stringResource(id = R.string.try_again_error_screen))
            }
        }
    }
}