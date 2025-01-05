package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(var generalRepository: GeneralRepository): ViewModel() {
    var movie = MutableLiveData<Movie>()


    fun loadMovie(movieId:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            movie.value =  generalRepository.getMovies().firstOrNull { it.id == movieId }
        }
    }
    fun addMovieToCart(movie: Movie,orderAmount:Int,userName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            generalRepository.insertMovie(movie = movie, orderAmount = orderAmount, userName = userName)
        }
    }
}