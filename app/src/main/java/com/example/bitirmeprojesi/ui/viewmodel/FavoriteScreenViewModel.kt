package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel@Inject constructor(private val generalRepository: GeneralRepository): ViewModel() {
    private val _favMovies = MutableStateFlow<List<FavoriteMovie>>(emptyList())
    val favMovies: StateFlow<List<FavoriteMovie>> get() = _favMovies



    fun loadFavoriteMovies() {
        viewModelScope.launch {
            val response = generalRepository.getFavMovies()
            _favMovies.value = response
            Log.e("FavoriteScreenViewModel",response.toString())
        }
    }
}