package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import com.example.bitirmeprojesi.util.toFavMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(private val generalRepository: GeneralRepository) :
    ViewModel() {

    private val _movie = MutableStateFlow(Movie())
    val movie: StateFlow<Movie> get() = _movie

    private val _existsInDb = MutableStateFlow(false)
    val existsInDb: StateFlow<Boolean> get() = _existsInDb

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _movie.value =
                generalRepository.getMovies().firstOrNull { it.id == movieId } ?: Movie()
            checkMovieInDb(movieId = movieId)
        }
    }
    // Check if the movie is already in the database
    private fun checkMovieInDb(movieId: Int) {
        viewModelScope.launch {
            val response = generalRepository.checkFavMovie(movieId = movieId)
            _existsInDb.value = response

        }
    }
    // Adds movie to favorites and update the existsInDb value
    fun addMovieToFav(movie: Movie,userRating:Double) {
        viewModelScope.launch {
            generalRepository.addMovieToFav(movie.toFavMovie(userRating = userRating))
            checkMovieInDb(movieId = movie.id)
        }
    }
    // Removes movie from favorites and update the existsInDb value
    fun deleteMovieFromFav(movieId: Int) {
        viewModelScope.launch {
            generalRepository.deleteFavMovie(movieId = movieId)
            checkMovieInDb(movieId = movieId)
        }
    }

    fun addMovieToCart(movie: Movie, orderAmount: Int, userName: String) {
        viewModelScope.launch {
            // Get the current cart items
            val currentCartItems = generalRepository.getMovieCart(userName)

            // Check if the movie already exists in the cart
            val existingCartItem = currentCartItems.firstOrNull { it.name == movie.name }

            if (existingCartItem != null) {
                // If the item exists, delete it
                generalRepository.deleteMovie(userName = userName, cartId = existingCartItem.cartId)

                // Reinsert the updated cart item with the new order amount
                generalRepository.insertMovie(movie = movie, orderAmount = existingCartItem.orderAmount + 1, userName = userName)
            } else {
                // If the item doesn't exist, insert it as a new entry
                generalRepository.insertMovie(movie = movie, orderAmount = orderAmount, userName = userName)
            }
        }
    }
}
