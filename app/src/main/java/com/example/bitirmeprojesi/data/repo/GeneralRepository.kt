package com.example.bitirmeprojesi.data.repo

import com.example.bitirmeprojesi.data.datasource.CartDataSource
import com.example.bitirmeprojesi.data.datasource.MoviesDataSource
import com.example.bitirmeprojesi.data.model.Movie


class GeneralRepository(
    var cartDataSource: CartDataSource,
    var moviesDataSource: MoviesDataSource
) {
suspend fun getMovies():List<Movie> = moviesDataSource.getAllMovies()

}