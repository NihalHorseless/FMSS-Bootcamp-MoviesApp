package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(var generalRepository: GeneralRepository): ViewModel()  {
}