package com.example.bitirmeprojesi.util

import com.example.bitirmeprojesi.data.model.cart.MovieCart
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie
import com.example.bitirmeprojesi.data.model.movie.Movie

// Converts a MovieCart object to a Movie object
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
// Converts a Movie object to a FavoriteMovie object
fun Movie.toFavMovie(userRating: Double) : FavoriteMovie {
    return FavoriteMovie(
        fav_id = this.id,
        name = this.name,
        image = this.image,
        rating = userRating,
        year = this.year,
        category = this.category
    )
}
// Converts the rating to a star emoji string
fun convertToStar(rating:Double) : String {
    return when (rating) {
        in 0.0 .. 2.0 -> "✦⟡⟡⟡⟡"
        in 2.1 .. 4.0 -> "✦✦⟡⟡⟡"
        in 4.1 .. 6.0-> "✦✦✦⟡⟡"
        in 6.1 .. 8.0 -> "✦✦✦✦⟡"
        in 8.1 .. 10.0 -> "✦✦✦✦✦"
        else -> "Invalid Rating"

    }
}