package com.example.bitirmeprojesi.util

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val movieId: Int)

@Serializable
data class Cart(val userName:String)

@Serializable
object Favorites

@Serializable
object Search

@Serializable
object Splash