package com.example.tinkoff_ronzhin_yaroslav_2023.network

object FilmApi {
    val retrofitServices: FilmApiService by lazy {
        retrofit.create(FilmApiService::class.java)
    }
}
