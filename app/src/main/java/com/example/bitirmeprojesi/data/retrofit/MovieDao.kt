package com.example.bitirmeprojesi.data.retrofit

import com.example.bitirmeprojesi.data.model.cart.CartResponse
import com.example.bitirmeprojesi.data.model.DeleteInsertResponse
import com.example.bitirmeprojesi.data.model.movie.MovieResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieDao {

    @GET("movies/getAllMovies.php")
    suspend fun getAllMovies(): MovieResponse

    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMovieCart(@Field("userName") userName:String): CartResponse

    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun deleteMovie(@Field("userName") userName:String,
                            @Field("cartId") cartId:Int): DeleteInsertResponse

    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun insertMovie(@Field("name")
                                name: String,
                            @Field("image")
                                image: String,
                            @Field("price")
                                price: Int,
                            @Field("category")
                                category: String,
                            @Field("rating")
                                rating: Double,
                            @Field("year")
                                year: Int,
                            @Field("director")
                                director: String,
                            @Field("description")
                                description: String,
                            @Field("orderAmount")
                                orderAmount: Int,
                            @Field("userName")
                                userName: String): DeleteInsertResponse

}