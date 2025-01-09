package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import com.example.bitirmeprojesi.ui.screens.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val generalRepository: GeneralRepository
) : ViewModel() {
    private val _favMovies = MutableStateFlow<List<FavoriteMovie>>(emptyList())
    val favMovies: StateFlow<List<FavoriteMovie>> get() = _favMovies

    private val _filteredFavMovies = MutableStateFlow<List<FavoriteMovie>>(emptyList())
    val filteredFavMovies: StateFlow<List<FavoriteMovie>> get() = _filteredFavMovies

    fun loadFavoriteMovies() {
        viewModelScope.launch {
            val response = generalRepository.getFavMovies()
            _favMovies.value = response
            _filteredFavMovies.value = response
        }
    }

    fun applyFilterAndSort(category: String?, sortOption: SortOption) {
        viewModelScope.launch {
            var filtered = _favMovies.value
            if (!category.isNullOrEmpty()) {
                filtered = filtered.filter { it.category == category }
            }
            _filteredFavMovies.value = when (sortOption) {
                SortOption.RATING -> filtered.sortedByDescending { it.rating }
                SortOption.YEAR -> filtered.sortedByDescending { it.year }
            }
        }
    }
}