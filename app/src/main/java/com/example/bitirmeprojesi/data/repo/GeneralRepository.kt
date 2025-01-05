package com.example.bitirmeprojesi.data.repo

import com.example.bitirmeprojesi.data.datasource.CartDataSource
import com.example.bitirmeprojesi.data.datasource.MoviesDataSource
import com.example.bitirmeprojesi.data.model.cart.Cart
import com.example.bitirmeprojesi.data.model.movie.Movie


class GeneralRepository(
    var cartDataSource: CartDataSource,
    var moviesDataSource: MoviesDataSource
) {

suspend fun getMovies():List<Movie> = moviesDataSource.getAllMovies()

suspend fun getMovieCart(userName:String):List<Cart> = cartDataSource.getMovieCart(userName = userName)

suspend fun insertMovie(movie:Movie,orderAmount:Int,userName: String) = cartDataSource.insertMovie(movie = movie, orderAmount = orderAmount,userName = userName )

suspend fun deleteMovie(userName: String,cartId: Int) = cartDataSource.deleteMovie(userName = userName, cartId = cartId)


}