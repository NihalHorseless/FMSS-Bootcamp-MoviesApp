package com.example.bitirmeprojesi.ui.screens

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie
import com.example.bitirmeprojesi.data.retrofit.ApiUtils.Companion.BASE_IMAGE_URL
import com.example.bitirmeprojesi.ui.theme.BitirmeProjesiTheme
import com.example.bitirmeprojesi.util.convertToStar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navigateToHome: () -> Unit,
                    onNavigateToDetail: (Int) -> Unit,
                    onNavigateToCart: (String) -> Unit) {

    val favoriteMovieList = remember { mutableStateListOf<FavoriteMovie>() }

    LaunchedEffect(Unit) {
        favoriteMovieList.add(FavoriteMovie(id = 9, name = "Django", image = "django.png", rating = 6.0))
        favoriteMovieList.add(FavoriteMovie(id = 8, name = "Catsby", image = "catsby.png", rating = 8.0))
        favoriteMovieList.add(FavoriteMovie(id = 6, name = "Batman", image = "batman.png", rating = 9.0))
        favoriteMovieList.add(FavoriteMovie(id = 5, name = "12 Monkeys", image = "12monkeys.png", rating = 7.0))


    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Favorite Movies") },
                navigationIcon = {
                    IconButton(onClick = navigateToHome) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_home_24),
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateToCart("onur_aslan")
                         }) {
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
            columns = GridCells.Fixed(4)
        ) {
            items(count = favoriteMovieList.count()) { index ->
                val movie = favoriteMovieList[index]
                Card(modifier = Modifier.padding(all = 4.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onNavigateToDetail(movie.id)
                            }
                    ) {
                        AsyncImage(
                            model = BASE_IMAGE_URL + movie.image,
                            contentDescription = null,
                            modifier = Modifier,
                            alignment = Alignment.Center
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "${convertToStar(movie.rating)}", fontSize = 16.sp, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}
