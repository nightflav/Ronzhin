package com.example.tinkoff_ronzhin_yaroslav_2023.network

import com.example.tinkoff_ronzhin_yaroslav_2023.data.film.Film
import com.example.tinkoff_ronzhin_yaroslav_2023.data.filmsJsonParse.FilmsList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val BASE_URL = "https://kinopoiskapiunofficial.tech"

const val API_KEY = " e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface FilmApiService {
    @Headers(
        "accept: application/json",
        "X-API-KEY: $API_KEY"
    )
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getListOfFilmsAsync(): FilmsList

    @Headers(
        "accept: application/json",
        "X-API-KEY: $API_KEY"
    )
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmByIdAsync(@Path("id") id: String) : Film
}