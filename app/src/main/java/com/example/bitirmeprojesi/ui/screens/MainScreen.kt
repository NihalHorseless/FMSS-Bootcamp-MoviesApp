package com.example.bitirmeprojesi.ui.screens

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.retrofit.ApiUtils.Companion.BASE_IMAGE_URL
import com.example.bitirmeprojesi.ui.viewmodel.MainScreenViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToCart: (String) -> Unit,
    onNavigateToFavs: () -> Unit,
    mainScreenViewModel: MainScreenViewModel
) {
    val movieList = mainScreenViewModel.movieList.collectAsState(initial = listOf())

    LaunchedEffect(Unit) {
        mainScreenViewModel.loadMovies()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Movies") },
                navigationIcon = {
                    IconButton(onClick = onNavigateToFavs) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_bookmarks_24),
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateToCart("onur_aslan") }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_shopping_cart_24),
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2)
        ) {
            items(count = movieList.value.count()) { index ->
                val movie = movieList.value[index]
                Card(modifier = Modifier.padding(all = 4.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToDetail(movie.id) }
                    ) {
                        AsyncImage(
                            model = BASE_IMAGE_URL + movie.image,
                            contentDescription = null
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "${movie.price} $", fontSize = 25.sp)
                        }
                    }
                }
            }
        }
    }
}