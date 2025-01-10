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

    private val _totalCost = MutableStateFlow(0)
    val totalCost: StateFlow<Int> get() = _totalCost

    fun calculateTotalPrice(movieCarts: List<MovieCart>): Int {
        var sum = 0
        for (movie in movieCarts) {
            sum += (movie.price * movie.orderAmount)
        }
        return sum
    }

    fun deleteCartItem(cartId: Int, userName: String) {
        viewModelScope.launch {
            try {
                val request = generalRepository.deleteMovie(userName = userName, cartId = cartId)
                Log.e("TAG", "${request.message}  ${request.success}")
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
                generalRepository.deleteMovie(
                    cartId = movieCart.cartId,
                    userName = movieCart.userName
                )

                // Update the cart in the repository
                generalRepository.insertMovie(
                    movie = updatedItem.toMovie(),
                    orderAmount = updatedItem.orderAmount,
                    userName = updatedItem.userName
                )
                refreshCart(movieCart.userName)


            } catch (e: Exception) {
                Log.e("CartScreenViewModel", "Error increasing amount: ${e.message}")
            }
        }
    }

    fun decreaseAmount(movieCart: MovieCart) {
        viewModelScope.launch {
            try {
                if (movieCart.orderAmount == 1) {
                    Log.e("decreaseBefore", movieCartMovies.value.toString())
                    generalRepository.deleteMovie(
                        cartId = movieCart.cartId,
                        userName = movieCart.userName
                    )
                    Log.e("decreaseAfter", movieCartMovies.value.toString())
                } else {
                    // Modify the order amount in the cart item directly
                    val updatedItem = movieCart.copy(orderAmount = movieCart.orderAmount - 1)
                    generalRepository.deleteMovie(
                        cartId = movieCart.cartId,
                        userName = movieCart.userName
                    )
                    generalRepository.insertMovie(
                        movie = updatedItem.toMovie(),
                        orderAmount = updatedItem.orderAmount,
                        userName = updatedItem.userName
                    )

                }

                refreshCart(userName = movieCart.userName)
            } catch (e: Exception) {
                Log.e("CartScreenViewModel", "Error decreasing amount: ${e.message}")
            }
        }
        Log.e("decreasedone", movieCartMovies.value.toString())

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
                _movieCartMovies.value = updatedCarts.sortedBy { it.name }
                _isCartEmpty.value = updatedCarts.isEmpty()
                _totalCost.value = calculateTotalPrice(movieCarts = updatedCarts)
            } catch (e: Exception) {
                _movieCartMovies.value = emptyList()
                _isCartEmpty.value = true
                Log.e("CartScreenViewModel", "Error refreshing cart: ${e.message}")
            }
        }
    }
}
