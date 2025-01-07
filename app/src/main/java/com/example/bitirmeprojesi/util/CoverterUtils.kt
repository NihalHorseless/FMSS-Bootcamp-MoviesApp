package com.example.bitirmeprojesi.util

import com.example.bitirmeprojesi.data.model.cart.MovieCart
import com.example.bitirmeprojesi.data.model.movie.Movie


fun MovieCart.toMovie(): Movie {
    return Movie(
        director = this.director,
        description = this.description,
        name = this.name,
        image = this.image,
        year = this.year,
        price = this.price,
        category = this.category,
        rating = this.rating
    )
}
fun convertToStar(rating:Double) : String {
    return when (rating) {
        in 0.0 .. 1.99 -> " ✦⟡⟡⟡⟡"
        in 2.0 .. 3.99 -> " ✦✦⟡⟡⟡"
        in 4.0 .. 5.99 -> " ✦✦✦⟡⟡"
        in 6.0 .. 7.99 -> " ✦✦✦✦⟡"
        in 8.0 .. 10.0 -> " ✦✦✦✦✦"
        else -> "Invalid Rating"

    }
}