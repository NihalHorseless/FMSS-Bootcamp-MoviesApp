package com.example.bitirmeprojesi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    var id: Int = 0 ,
    var name: String = "",
    var image: String = "",
    var price: Int = 0 ,
    var category: String = "",
    var rating: Double = 1.0,
    var year: Int = 0 ,
    var director: String = "",
    var description: String = ""
) {
}