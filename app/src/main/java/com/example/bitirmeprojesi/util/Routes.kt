package com.example.bitirmeprojesi.util

import com.example.bitirmeprojesi.data.model.movie.Movie
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val movieId: Int)

@Serializable
object Cart