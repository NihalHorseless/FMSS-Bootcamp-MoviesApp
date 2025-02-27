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
class SearchScreenViewModel@Inject constructor(private val generalRepository: GeneralRepository) :
    ViewModel()  {
    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> get() = _movieList

    // To hold the search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _filteredMovies = MutableStateFlow<List<Movie>>(emptyList())
    val filteredMovies: StateFlow<List<Movie>> get() = _filteredMovies

    init {
        fetchMovies()
    }
    // Gets the list of movies from the API
    private fun fetchMovies() {
        viewModelScope.launch {
            val response = generalRepository.getMovies()
            _movieList.value = response
        }
    }
    // Filters the movie list based on the search query
     fun onSearch(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
            _filteredMovies.value = if (query.isEmpty()) {
                _movieList.value
            } else {
                _movieList.value.filter { it.name.contains(query, ignoreCase = true)
                }
            }
        }

    }
}