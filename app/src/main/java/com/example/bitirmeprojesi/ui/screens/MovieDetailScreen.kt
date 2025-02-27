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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.retrofit.ApiUtils.Companion.BASE_IMAGE_URL
import com.example.bitirmeprojesi.ui.viewmodel.MovieDetailScreenViewModel
import com.example.bitirmeprojesi.util.convertToStar

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navigateToCart: (String) -> Unit,
    navigateBack: () -> Unit,
    movieDetailScreenViewModel: MovieDetailScreenViewModel
) {
    val movie = movieDetailScreenViewModel.movie.collectAsState(initial = Movie())
    val movieExistsInDb = movieDetailScreenViewModel.existsInDb.collectAsState(initial = false)
    val showRatingDialog = remember { mutableStateOf(false) }
    val userRating = remember { mutableFloatStateOf(0f) } // Default rating value

    LaunchedEffect(key1 = true) {
        movieDetailScreenViewModel.loadMovie(movieId)
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                IconButton(
                    onClick = { navigateBack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_content_description),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                IconButton(
                    onClick = { navigateToCart("onur_aslan") },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_shopping_cart_24),
                        contentDescription = stringResource(R.string.cart_button_content_description),
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.background
                            ),
                            startY = 0f,
                            endY = 1000f
                        )
                    )
            )
            Column(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = BASE_IMAGE_URL + movie.value.image,
                    contentDescription = stringResource(R.string.movie_poster_content_description),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(7 / 6f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = movie.value.name,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly, // Distribute space evenly
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rating and Reviews
                        MovieDetailSection(text = convertToStar(movie.value.rating), iconRes = R.drawable.baseline_reviews_24, color = MaterialTheme.colorScheme.primary)

                        // Separator
                        LineSeperator()

                        // Movie Category
                        MovieDetailSection(text = movie.value.category, iconRes = R.drawable.baseline_local_movies_24, color = Color.White)

                        LineSeperator()

                        // Movie Director
                        MovieDetailSection(text = movie.value.director, iconRes = R.drawable.baseline_video_camera_director, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = movie.value.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            modifier = Modifier,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            onClick = {
                                movieDetailScreenViewModel.addMovieToCart(
                                    movie = movie.value,
                                    userName = "onur_aslan",
                                    orderAmount = 1
                                )
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_24),
                                contentDescription = stringResource(R.string.add_to_cart_content_description)
                            )
                        }

                        IconButton(
                            modifier = Modifier,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            onClick = {
                                if (movieExistsInDb.value) {
                                    movieDetailScreenViewModel.deleteMovieFromFav(movieId = movie.value.id)
                                } else {
                                    showRatingDialog.value = true
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(if (movieExistsInDb.value) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24),
                                contentDescription = stringResource(R.string.bookmark_content_description)
                            )

                        }
                    }

                }
            }
        }
    }

    // Dialog for rating slider
    if (showRatingDialog.value) {
        AlertDialog(containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { showRatingDialog.value = false },
            title = { Text(text = "Rate this Movie") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Select your rating:")
                    Spacer(modifier = Modifier.height(16.dp))
                    Slider(
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.secondary,
                            activeTrackColor = MaterialTheme.colorScheme.secondary
                        ),
                        value = userRating.floatValue,
                        onValueChange = {
                            userRating.floatValue =
                                it.toInt().toFloat() // Restrict to discrete steps
                        },
                        valueRange = 0f..10f,
                        steps = 10
                    )
                    Text(text = "Rating: ${userRating.floatValue}")
                }
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    onClick = {
                        movieDetailScreenViewModel.addMovieToFav(
                            movie = movie.value,
                            userRating = userRating.floatValue.toDouble()
                        )
                        showRatingDialog.value = false
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    onClick = { showRatingDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }

}
@Composable
fun MovieDetailSection(text: String, iconRes: Int,color: Color) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = stringResource(R.string.review_info_content_description),
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = color
        )
    }
}
@Composable
fun LineSeperator() {
    // Line Separator for Sections
    Spacer(modifier = Modifier.width(1.dp)) // Adjusts width for line
    Box(
        modifier = Modifier
            .height(32.dp)
            .width(2.dp)
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
    )
    Spacer(modifier = Modifier.width(1.dp))
}
