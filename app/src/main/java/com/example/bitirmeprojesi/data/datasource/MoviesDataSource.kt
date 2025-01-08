package com.example.bitirmeprojesi.data.datasource

import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.retrofit.MovieDao
import com.example.bitirmeprojesi.room.FavMovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesDataSource(var movieDao: MovieDao,var favoriteMovieDao: FavMovieDao) {

    suspend fun getAllMovies(): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext movieDao.getAllMovies().movies
    }
    suspend fun update(updatedFavMovie:FavoriteMovie) {
        favoriteMovieDao.updateMovie(updatedFavMovie)
    }

    suspend fun delete(movieId: Int) {
        favoriteMovieDao.deleteById(movieId = movieId)
    }

    suspend fun loadFavMovies(): List<FavoriteMovie> = withContext(Dispatchers.IO) {
        return@withContext favoriteMovieDao.getAll()
    }
    suspend fun addMovieToFav(favMovie:FavoriteMovie) {
        favoriteMovieDao.addMovieToFav(favMovie = favMovie)
    }
    suspend fun checkFavMovie(movieId:Int) : Boolean = withContext(Dispatchers.IO) {
        return@withContext favoriteMovieDao.doesFavMovieExist(movieId = movieId)
    }
}