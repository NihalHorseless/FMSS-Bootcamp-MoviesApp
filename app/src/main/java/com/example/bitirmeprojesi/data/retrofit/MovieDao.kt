package com.example.bitirmeprojesi.data.retrofit

import com.example.bitirmeprojesi.data.model.cart.CartResponse
import com.example.bitirmeprojesi.data.model.DeleteInsertResponse
import com.example.bitirmeprojesi.data.model.cart.CartRequest
import com.example.bitirmeprojesi.data.model.movie.MovieResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieDao {

    @GET("movies/getAllMovies.php")
    suspend fun getAllMovies(): MovieResponse

    @POST("movies/getMovieCart.php")
    suspend fun getMovieCart(@Field("userName") userName:String): CartResponse

    @POST("movies/deleteMovie.php")
    suspend fun deleteMovie(@Field("userName") userName:String,
                            @Field("cartId") cartId:Int): DeleteInsertResponse

    @POST("movies/insertMovie.php")
    suspend fun insertMovie(@Body movie_cart: CartRequest): DeleteInsertResponse

}