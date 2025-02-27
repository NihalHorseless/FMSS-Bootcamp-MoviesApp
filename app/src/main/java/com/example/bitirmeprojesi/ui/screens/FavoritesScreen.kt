package com.example.bitirmeprojesi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie
import com.example.bitirmeprojesi.data.retrofit.ApiUtils.Companion.BASE_IMAGE_URL
import com.example.bitirmeprojesi.ui.viewmodel.FavoriteScreenViewModel
import com.example.bitirmeprojesi.util.convertToStar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navigateToHome: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    favoriteScreenViewModel: FavoriteScreenViewModel
) {
    val favoriteMovieList = favoriteScreenViewModel.filteredFavMovies.collectAsState(initial = emptyList())
    val isGridView = remember { mutableStateOf(true) } // Checks whether the view is grid or list
    val showFilterDialog = remember { mutableStateOf(false) } // Controls visibility of the filter section

    LaunchedEffect(Unit) {
        favoriteScreenViewModel.loadFavoriteMovies()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.tertiary),
                title = { Text(text = stringResource(R.string.favorites_title), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
                        onClick = navigateToHome
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_home_24),
                            contentDescription = stringResource(R.string.home_content_description),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.LightGray),
                        onClick = {
                            isGridView.value = !isGridView.value // Toggle between grid and list
                        }) {
                        Icon(
                            painter = painterResource(
                                if (isGridView.value) R.drawable.baseline_grid_off_24
                                else R.drawable.baseline_grid_on_24
                            ),
                            contentDescription = stringResource(R.string.toggle_layout_content_description_grid)
                        )
                    }

                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {showFilterDialog.value = true}, contentColor = Color.White, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(
                    painter = painterResource(R.drawable.baseline_filter_list_24),
                    contentDescription = stringResource(R.string.toggle_layout_content_description_grid)
                )
            }
        }
    ) { paddingValues ->
        if (isGridView.value) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.surface),
                columns = GridCells.Fixed(4)
            ) {
                items(count = favoriteMovieList.value.size) { index ->
                    val movie = favoriteMovieList.value[index]
                    MovieItemCard(movie = movie, onClick = { onNavigateToDetail(movie.fav_id) })
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                items(favoriteMovieList.value) { movie ->
                    MovieListItemCard(movie = movie, onClick = { onNavigateToDetail(movie.fav_id) })
                }
            }
        }
        // Show the filter section if showFilterDialog is true
        if (showFilterDialog.value) {
            FilterDialog(
                onApply = { category, sortOption ->
                    favoriteScreenViewModel.applyFilterAndSort(category, sortOption)
                    showFilterDialog.value = false
                },
                onDismiss = { showFilterDialog.value = false }
            )
        }
    }
}

@Composable
fun MovieItemCard(movie: FavoriteMovie, onClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(all = 4.dp)
        .background(MaterialTheme.colorScheme.surface)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            AsyncImage(
                model = BASE_IMAGE_URL + movie.image,
                contentDescription = stringResource(R.string.movie_poster_content_description),
                modifier = Modifier.aspectRatio(ratio = 2 / 3f),
                alignment = Alignment.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = convertToStar(movie.rating),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun MovieListItemCard(movie: FavoriteMovie, onClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(all = 4.dp)
        .background(MaterialTheme.colorScheme.surface)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                model = BASE_IMAGE_URL + movie.image,
                contentDescription = stringResource(R.string.movie_poster_content_description),
                modifier = Modifier.size(width = 90.dp, height = 135.dp),
                alignment = Alignment.Center
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = movie.name, fontSize = 20.sp, textAlign = TextAlign.Center, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${movie.year} - ${movie.category}", fontSize = 18.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = convertToStar(movie.rating),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )

            }
        }
    }
}
@Composable
fun FilterDialog(
    onApply: (String?, SortOption) -> Unit,
    onDismiss: () -> Unit
) {
    // Example filter options
    val categories = listOf("Action", "Fantastic", "Drama", "Science Fiction")
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val selectedSortOption = remember { mutableStateOf(SortOption.RATING) }

    // Dialog UI
    AlertDialog(containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.filter_and_sort_title)) },
        text = {
            Column {
                Text(text = stringResource(R.string.category_label))
                categories.forEach { category ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedCategory.value = category },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.secondary),
                            selected = selectedCategory.value == category,
                            onClick = { selectedCategory.value = category },
                            label = { Text(text = category) },
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = stringResource(R.string.sort_by_label))
                SortOption.entries.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedSortOption.value = option },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primary),
                            selected = selectedSortOption.value == option,
                            onClick = { selectedSortOption.value = option },
                            label = { Text(option.displayName) },
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(colors = ButtonDefaults.textButtonColors(contentColor = Color.White),onClick = { onApply(selectedCategory.value, selectedSortOption.value) }) {
                Text(text = stringResource(R.string.apply_button_text))
            }
        },
        dismissButton = {
            TextButton(colors = ButtonDefaults.textButtonColors(contentColor = Color.White),onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel_button_text))
            }
        }
    )
}
enum class SortOption(val displayName: String) {
    RATING("Rating"),
    YEAR("Release Year")
}

