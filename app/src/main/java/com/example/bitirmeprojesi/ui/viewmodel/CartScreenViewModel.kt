package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.model.cart.MovieCart
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import com.example.bitirmeprojesi.util.toMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(private val generalRepository: GeneralRepository) :
    ViewModel() {

    private val _movieCartMovies = MutableStateFlow<List<MovieCart>>(emptyList())
    val movieCartMovies: StateFlow<List<MovieCart>> get() = _movieCartMovies

    private val _isCartEmpty = MutableStateFlow(false)
    val isCartEmpty: StateFlow<Boolean> get() = _isCartEmpty


    fun deleteCartItem(cartId: Int, userName: String) {
        viewModelScope.launch {
            try {
                val request = generalRepository.deleteMovie(userName = userName, cartId = cartId)
                Log.e("TAG", "${request.message}  ${request.success}")
                refreshCart(userName) // Refresh data after deletion
            } catch (e: Exception) {
                Log.e("CartScreenViewModel", "Error deleting cart item: ${e.message}")
            }
        }
    }

    fun increaseAmount(movieCart: MovieCart) {
        viewModelScope.launch {
            try {
                // Modify the order amount in the cart item directly
                val updatedItem = movieCart.copy(orderAmount = movieCart.orderAmount + 1)

                // Update the cart in the repository
                generalRepository.insertMovie(
                    movie = updatedItem.toMovie(),
                    orderAmount = updatedItem.orderAmount,
                    userName = updatedItem.userName
                )

                // Update the UI state with the modified cart item without reloading the entire list
                _movieCartMovies.value = _movieCartMovies.value.map {
                    if (it.cartId == movieCart.cartId) {
                        updatedItem // Update the modified item
                    } else {
                        it
                    }
                }
            } catch (e: Exception) {
                Log.e("CartScreenViewModel", "Error increasing amount: ${e.message}")
            }
        }
    }

    fun decreaseAmount(movieCart: MovieCart) {
        viewModelScope.launch {
            try {
                if (movieCart.orderAmount == 1) {
                    deleteCartItem(cartId = movieCart.cartId, userName = movieCart.userName)
                } else {
                    // Modify the order amount in the cart item directly
                    val updatedItem = if (movieCart.orderAmount > 1) {
                        movieCart.copy(orderAmount = movieCart.orderAmount - 1)
                    } else {
                        movieCart // Don't decrease below 1
                    }
                    generalRepository.insertMovie(
                        movie = updatedItem.toMovie(),
                        orderAmount = updatedItem.orderAmount,
                        userName = updatedItem.userName
                    )
                    // Update the UI state with the modified cart item without reloading the entire list
                    _movieCartMovies.value = _movieCartMovies.value.map {
                        if (it.cartId == movieCart.cartId) {
                            updatedItem // Update the modified item
                        } else {
                            it
                        }
                    }
                }


            } catch (e: Exception) {
                Log.e("CartScreenViewModel", "Error decreasing amount: ${e.message}")
            }
        }
    }

    fun deleteAllCarts(cartItems: List<MovieCart>) {
        viewModelScope.launch {
            try {
                cartItems.forEach { item ->
                    generalRepository.deleteMovie(userName = item.userName, cartId = item.cartId)
                }
                refreshCart("onur_aslan") // Refresh data after deletion
            } catch (e: Exception) {
                Log.e("CartScreenViewModel", "Error deleting all carts: ${e.message}")
            }
        }
    }

    fun refreshCart(userName: String) {
        viewModelScope.launch {
            try {
                val updatedCarts = generalRepository.getMovieCart(userName)
                _movieCartMovies.value = updatedCarts
                _isCartEmpty.value = updatedCarts.isEmpty()
            } catch (e: Exception) {
                _movieCartMovies.value = emptyList()
                _isCartEmpty.value = true
                Log.e("CartScreenViewModel", "Error refreshing cart: ${e.message}")
            }
        }
    }
}
