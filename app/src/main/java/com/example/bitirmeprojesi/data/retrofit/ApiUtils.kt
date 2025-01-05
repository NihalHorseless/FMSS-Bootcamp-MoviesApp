package com.example.bitirmeprojesi.data.retrofit

class ApiUtils {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"

        val BASE_IMAGE_URL = "http://kasimadalan.pe.hu/movies/images/"

        fun getMovieDao() : MovieDao {
            return RetrofitClient.getClient(BASE_URL).create(MovieDao::class.java)
        }
    }
}