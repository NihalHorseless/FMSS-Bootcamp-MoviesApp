package com.example.bitirmeprojesi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.retrofit.ApiUtils.Companion.BASE_IMAGE_URL
import com.example.bitirmeprojesi.ui.viewmodel.MainScreenViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToCart: (String) -> Unit,
    onNavigateToFavs: () -> Unit,
    onNavigateToSearch: () -> Unit,
    mainScreenViewModel: MainScreenViewModel
) {
    val movieList = mainScreenViewModel.movieList.collectAsState(initial = listOf())

    LaunchedEffect(Unit) {
        mainScreenViewModel.loadMovies()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.tertiary),
                title = { Text(text = "Movies") },
                navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
                        onClick = onNavigateToFavs
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_bookmarks_24),
                            contentDescription = ""
                        )
                    }

                },
                actions = {

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                        onClick = { onNavigateToCart("onur_aslan") }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_shopping_cart_24),
                            contentDescription = ""
                        )
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToSearch, contentColor = Color.White, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(
                    painter = painterResource(R.drawable.baseline_manage_search_24),
                    contentDescription = ""
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            MoviesSection(
                sectionName = "Highly Rated",
                onNavigateToDetail = onNavigateToDetail,
                movieList = mainScreenViewModel.getTopRatedMovies(movieList.value)
            )
            MoviesSection(
                sectionName = "Masterpieces by Tarantino",
                onNavigateToDetail = onNavigateToDetail,
                movieList = mainScreenViewModel.getMoviesByDirector(
                    passedMovieList = movieList.value,
                    directorName = "Quentin Tarantino"
                )
            )

        }


    }
}

@Composable
fun MoviesSection(sectionName: String, onNavigateToDetail: (Int) -> Unit, movieList: List<Movie>) {
    Text(text = "$sectionName", modifier = Modifier.fillMaxWidth())
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = movieList.count()) { index ->
            val movie = movieList[index]
            Card(modifier = Modifier.padding(all = 4.dp)) {
                Column(
                    modifier = Modifier
                        .width(150.dp) // Set a fixed width for each item
                        .clickable { onNavigateToDetail(movie.id) }
                ) {
                    AsyncImage(
                        model = BASE_IMAGE_URL + movie.image,
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(ratio = 2 / 3f)
                    )

                }
            }
        }
    }
}