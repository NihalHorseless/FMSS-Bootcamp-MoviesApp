package com.example.bitirmeprojesi.data.datasource

import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.retrofit.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesDataSource(var movieDao: MovieDao) {

    suspend fun getAllMovies(): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext movieDao.getAllMovies().movies
    }
}