package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val generalRepository: GeneralRepository) :
    ViewModel() {

    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> get() = _movieList

    init {
        loadMovies()
    }
    fun getMoviesByDirector(passedMovieList: List<Movie>,directorName: String): List<Movie> {
        return passedMovieList.filter { it.director.equals(directorName, ignoreCase = true) }
    }
    fun getTopRatedMovies(passedMovieList:List<Movie>): List<Movie> {
        return passedMovieList.sortedByDescending { it.rating }.take(5)
    }

    fun loadMovies() {
        viewModelScope.launch {
            _movieList.value = generalRepository.getMovies()
        }
    }
}
