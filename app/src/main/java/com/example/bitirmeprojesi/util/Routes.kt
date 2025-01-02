package com.example.bitirmeprojesi.util

import com.example.bitirmeprojesi.data.model.Movies
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val movieName: String,val movieImage:String,val moviePrice: Int)

@Serializable
object Cart