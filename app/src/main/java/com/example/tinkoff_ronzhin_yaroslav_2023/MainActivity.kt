package com.example.tinkoff_ronzhin_yaroslav_2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tinkoff_ronzhin_yaroslav_2023.navigation.MainNav
import com.example.tinkoff_ronzhin_yaroslav_2023.ui.theme.Tinkoff_ronzhin_yaroslav_2023Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tinkoff_ronzhin_yaroslav_2023Theme {
                MainNav()
            }
        }
    }
}