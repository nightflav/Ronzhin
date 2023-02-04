package com.example.tinkoff_ronzhin_yaroslav_2023.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.tinkoff_ronzhin_yaroslav_2023.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController) {
    var animationState by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (animationState) 1f else 0f,
        animationSpec = tween(2000)
    )

    LaunchedEffect(key1 = true) {
        animationState = true
        delay(2000)
        animationState = false
        navController.navigate(Screens.Main.screenId)
    }

    MainSplashScreen(alphaAnimation.value)
}

@Composable
private fun MainSplashScreen(alpha: Float) {
    Box(
        modifier = Modifier.fillMaxSize()
            .alpha(alpha),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Films App by \n Ronzhin Yaroslav", textAlign = TextAlign.Center)
    }
}