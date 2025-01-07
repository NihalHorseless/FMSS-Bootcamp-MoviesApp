package com.example.bitirmeprojesi.data.datasource

import com.example.bitirmeprojesi.data.model.DeleteInsertResponse
import com.example.bitirmeprojesi.data.model.cart.MovieCart
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.retrofit.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartDataSource(var movieDao:MovieDao) {

    suspend fun insertMovie(movie: Movie,orderAmount:Int,userName: String) {
        movieDao.insertMovie(
            name = movie.name,
            image = movie.image,
            orderAmount = orderAmount,
            userName = userName,
            year = movie.year,
            price = movie.price,
            rating = movie.rating,
            category = movie.category,
            director = movie.director,
            description = movie.description)
    }
    suspend fun getMovieCart(userName: String): List<MovieCart> = withContext(Dispatchers.IO) {
        try {
            val response = movieDao.getMovieCart(userName)
            if (response.movie_cart.isNullOrEmpty()) {
                return@withContext emptyList()
            } else {
                return@withContext response.movie_cart
            }
        } catch (e: Exception) {
            println("Error fetching cart: ${e.message}")
            return@withContext emptyList()
        }
    }
    suspend fun deleteMovie(userName: String,cartId:Int): DeleteInsertResponse = withContext(Dispatchers.IO){
        return@withContext movieDao.deleteMovie(userName = userName,cartId = cartId)
    }

}