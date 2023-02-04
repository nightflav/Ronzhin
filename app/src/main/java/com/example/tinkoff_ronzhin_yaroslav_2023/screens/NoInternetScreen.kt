package com.example.tinkoff_ronzhin_yaroslav_2023.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tinkoff_ronzhin_yaroslav_2023.model.AppViewModel

@Composable
fun NoInternetScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "No Internet Connection")
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Try again")
            }
        }
    }
}