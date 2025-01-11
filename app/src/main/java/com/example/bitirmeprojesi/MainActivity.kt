package com.example.bitirmeprojesi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.bitirmeprojesi.ui.screens.ScreenNavigation
import com.example.bitirmeprojesi.ui.theme.BitirmeProjesiTheme
import com.example.bitirmeprojesi.ui.viewmodel.CartScreenViewModel
import com.example.bitirmeprojesi.ui.viewmodel.FavoriteScreenViewModel
import com.example.bitirmeprojesi.ui.viewmodel.MainScreenViewModel
import com.example.bitirmeprojesi.ui.viewmodel.MovieDetailScreenViewModel
import com.example.bitirmeprojesi.ui.viewmodel.SearchScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainScreenViewModel: MainScreenViewModel by viewModels()
        val movieDetailScreenViewModel: MovieDetailScreenViewModel by viewModels()
        val cartScreenViewModel: CartScreenViewModel by viewModels()
        val favoriteScreenViewModel: FavoriteScreenViewModel by viewModels()
        val searchScreenViewModel: SearchScreenViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            BitirmeProjesiTheme {
                ScreenNavigation(mainScreenViewModel,movieDetailScreenViewModel,cartScreenViewModel,favoriteScreenViewModel,searchScreenViewModel)
            }
        }
    }
}

