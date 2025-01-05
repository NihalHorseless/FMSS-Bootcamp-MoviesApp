package com.example.bitirmeprojesi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.retrofit.ApiUtils.Companion.BASE_IMAGE_URL
import com.example.bitirmeprojesi.ui.theme.ReallyDark
import com.example.bitirmeprojesi.ui.theme.roboto_medium
import com.example.bitirmeprojesi.ui.theme.roboto_regular
import com.example.bitirmeprojesi.ui.viewmodel.MovieDetailScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    navigateToCart: () -> Unit,
    navigateToMain: () -> Unit,
    movieDetailScreenViewModel: MovieDetailScreenViewModel
) {
    var movie = movieDetailScreenViewModel.movie.observeAsState(Movie())

    LaunchedEffect(key1 = true) {
        movieDetailScreenViewModel.loadMovie(movieId)
    }
    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Adjust the height to your needs
                .background(Color.Transparent) // Make it transparent
        ) {
            IconButton(
                onClick = { navigateToMain() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { navigateToCart() },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_shopping_cart_24),
                    contentDescription = "Cart",
                    tint = Color.White
                )
            }
        }
    })
    { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                ReallyDark
                            ),
                            startY = 0f,
                            endY = 1000f
                        )
                    )
            )

            // Main Content
            Column(modifier = Modifier.fillMaxSize()) {
                // AsyncImage Section
                AsyncImage(
                    model = BASE_IMAGE_URL + movie.value.image,
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(7 / 6f)
                )

                // Text and Details Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = movie.value.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(roboto_regular),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly, // Distribute space evenly
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rating and Reviews
                        Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_reviews_24),
                                contentDescription = "Review Information",
                                modifier = Modifier.size(16.dp), tint = Color.Gray
                            )
                            Text(text = "${movie.value.rating} ✦✦✦⟡⟡", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }

                        // Separator
                        Spacer(modifier = Modifier.width(1.dp)) // Adjust width for thicker/thinner line
                        Box(modifier = Modifier.height(32.dp).width(2.dp).background(MaterialTheme.colorScheme.onBackground.copy(alpha=0.5f)))
                        Spacer(modifier = Modifier.width(1.dp))

                        Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_local_movies_24),
                                contentDescription = "Review Information",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Gray
                            )
                            Text(text = "${movie.value.category}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }

                        // Separator (Optional)
                        Spacer(modifier = Modifier.width(1.dp)) // Adjust width for thicker/thinner line
                        Box(modifier = Modifier.height(32.dp).width(2.dp).background(MaterialTheme.colorScheme.onBackground.copy(alpha=0.5f)))
                        Spacer(modifier = Modifier.width(1.dp))

                        Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_video_camera_director),
                            contentDescription = "Review Information",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                        Text(text = "${movie.value.director}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = movie.value.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }

    }
}