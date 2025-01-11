package com.example.bitirmeprojesi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
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
                title = {
                    Text(
                        text = stringResource(R.string.movies),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
                        onClick = onNavigateToFavs
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_bookmarks_24),
                            contentDescription = stringResource(R.string.bookmark_content_description)
                        )
                    }

                },
                actions = {

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.LightGray),
                        onClick = { onNavigateToCart("onur_aslan") }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_shopping_cart_24),
                            contentDescription = stringResource(R.string.cart_button_content_description)
                        )
                    }

                }
            )
        },
        // Floating action button for navigating to search screen
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToSearch,
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_manage_search_24),
                    contentDescription = stringResource(R.string.search_button_desc)
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
            // Section for highly rated movies
            MoviesSection(
                sectionName = stringResource(R.string.highly_rated),
                onNavigateToDetail = onNavigateToDetail,
                movieList = mainScreenViewModel.getTopRatedMovies(movieList.value)
            )
            // Section for movies by Quentin Tarantino
            MoviesSection(
                sectionName = stringResource(R.string.masterpieces_by_tarantino),
                onNavigateToDetail = onNavigateToDetail,
                movieList = mainScreenViewModel.getMoviesByDirector(
                    passedMovieList = movieList.value,
                    directorName = stringResource(R.string.sample_movie_director)
                )
            )

        }


    }
}

@Composable
fun MoviesSection(sectionName: String, onNavigateToDetail: (Int) -> Unit, movieList: List<Movie>) {
    // Section title
    Text(
        text = sectionName,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold
    )
    // Horizontal list of movie cards
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
                        .width(150.dp)
                        .clickable { onNavigateToDetail(movie.id) }
                ) {
                    var isImageLoading by remember { mutableStateOf(true) }

                    Box(
                        modifier = Modifier
                            .aspectRatio(ratio = 2 / 3f)
                    ) {
                        if (isImageLoading) {
                            LottiePlaceholder(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                animationRes = R.raw.load_animation // Replace with your Lottie file
                            )
                        }

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(BASE_IMAGE_URL + movie.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            onState = { state ->
                                if (state is AsyncImagePainter.State.Success) {
                                    isImageLoading = false
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LottiePlaceholder(
    modifier: Modifier = Modifier,
    animationRes: Int
) {
    // Property values for Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    // Displays the Lottie animation
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}