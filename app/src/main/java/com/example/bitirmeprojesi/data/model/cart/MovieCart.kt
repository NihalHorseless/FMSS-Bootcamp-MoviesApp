package com.example.bitirmeprojesi.data.model.cart

data class MovieCart(
    var cartId: Int = 0,
    var name: String = "None",
    var image: String = "None",
    var price: Int = 0,
    var category: String = "None",
    var rating: Double = 0.0,
    var year: Int = 0,
    var director: String = "None",
    var description: String = "None",
    var orderAmount: Int = 0,
    var userName: String = "None"
)

