package com.example.bitirmeprojesi.util

import com.example.bitirmeprojesi.data.model.cart.CartRequest
import com.example.bitirmeprojesi.data.model.movie.Movie

fun Movie.convertCartToRequest(orderAmount:Int,userName:String): CartRequest {
    return CartRequest(
        name = this.name,
        image = this.image,
        price = this.price,
        category = this.category,
        rating = this.rating,
        year = this.year,
        director = this.director,
        description = this.description,
        orderAmount = orderAmount,
        userName = userName
    )
}
