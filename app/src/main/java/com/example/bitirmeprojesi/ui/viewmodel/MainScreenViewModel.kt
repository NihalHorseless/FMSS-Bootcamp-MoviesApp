package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.model.cart.Cart
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(var generalRepository: GeneralRepository) :
    ViewModel() {
    var movieList = MutableLiveData<List<Movie>>()
    init {
        loadMovies()
    }
    fun loadMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            movieList.value =  generalRepository.getMovies()
        }
    }


}