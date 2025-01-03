package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(var generalRepository: GeneralRepository): ViewModel() {
    
}