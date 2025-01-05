package com.example.bitirmeprojesi.data.datasource

import com.example.bitirmeprojesi.data.model.cart.Cart
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.retrofit.MovieDao
import com.example.bitirmeprojesi.util.convertCartToRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartDataSource(var movieDao:MovieDao) {

    suspend fun insertMovie(movie: Movie,orderAmount:Int,userName: String) {
        movieDao.insertMovie(movie_cart = movie.convertCartToRequest(orderAmount = orderAmount, userName = userName))
    }
    suspend fun getMovieCart(userName: String): List<Cart> = withContext(Dispatchers.IO) {
       return@withContext movieDao.getMovieCart(userName = userName).movie_cart
    }
    suspend fun deleteMovie(userName: String,cartId:Int) {
        movieDao.deleteMovie(userName = userName,cartId = cartId)
    }

}