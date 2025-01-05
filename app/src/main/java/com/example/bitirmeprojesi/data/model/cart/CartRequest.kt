package com.example.bitirmeprojesi.data.model.cart


data class CartRequest(
    var name: String,
    var image: String,
    var price: Int,
    var category: String,
    var rating: Double,
    var year: Int,
    var director: String,
    var description: String,
    var orderAmount: Int,
    var userName: String
)