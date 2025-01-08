package com.example.bitirmeprojesi.data.repo

import com.example.bitirmeprojesi.data.datasource.CartDataSource
import com.example.bitirmeprojesi.data.datasource.MoviesDataSource
import com.example.bitirmeprojesi.data.model.DeleteInsertResponse
import com.example.bitirmeprojesi.data.model.cart.MovieCart
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie
import com.example.bitirmeprojesi.data.model.movie.Movie


class GeneralRepository(
    var cartDataSource: CartDataSource,
    var moviesDataSource: MoviesDataSource
) {

suspend fun getMovies():List<Movie> = moviesDataSource.getAllMovies()

suspend fun getMovieCart(userName:String):List<MovieCart> = cartDataSource.getMovieCart(userName = userName)

suspend fun insertMovie(movie:Movie,orderAmount:Int,userName: String) = cartDataSource.insertMovie(movie = movie, orderAmount = orderAmount,userName = userName )

suspend fun deleteMovie(userName: String,cartId: Int):DeleteInsertResponse = cartDataSource.deleteMovie(userName = userName, cartId = cartId)

suspend fun addMovieToFav(favoriteMovie: FavoriteMovie) = moviesDataSource.addMovieToFav(favMovie = favoriteMovie)

suspend fun deleteFavMovie(movieId: Int) = moviesDataSource.delete(movieId = movieId)

suspend fun updateFavMovie(favoriteMovie: FavoriteMovie) = moviesDataSource.update(updatedFavMovie = favoriteMovie)

suspend fun getFavMovies():List<FavoriteMovie> = moviesDataSource.loadFavMovies()

suspend fun checkFavMovie(movieId:Int):Boolean = moviesDataSource.checkFavMovie(movieId = movieId)
}