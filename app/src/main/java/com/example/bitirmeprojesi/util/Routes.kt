package com.example.bitirmeprojesi.util

import com.example.bitirmeprojesi.data.model.movie.Movie
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val movieId: Int)

@Serializable
data class Cart(val userName:String)

@Serializable
object Favorites