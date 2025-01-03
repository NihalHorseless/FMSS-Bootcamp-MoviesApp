package com.example.bitirmeprojesi.data.retrofit

import com.example.bitirmeprojesi.data.model.MovieResponse
import retrofit2.http.GET

interface MovieDao {

    @GET("movies/getAllMovies.php")
    suspend fun getAllMovies(): MovieResponse


}