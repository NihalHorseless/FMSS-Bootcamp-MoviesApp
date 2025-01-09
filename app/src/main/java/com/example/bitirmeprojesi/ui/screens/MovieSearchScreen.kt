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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.movie.Movie
import com.example.bitirmeprojesi.data.retrofit.ApiUtils.Companion.BASE_IMAGE_URL
import com.example.bitirmeprojesi.ui.viewmodel.SearchScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(onNavigateToDetail: (Int) -> Unit,
                      onNavigateToHome: () -> Unit,
                      searchViewModel: SearchScreenViewModel) {
    val movies = searchViewModel.movieList.collectAsState(initial = listOf())
    val filteredMovies = searchViewModel.filteredMovies.collectAsState(initial = listOf())
    val query = searchViewModel.searchQuery.collectAsState(initial = "")
Scaffold(topBar =  {
CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.tertiary),title = {
    Text(text = "Search Movies")
},navigationIcon = {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
        onClick = onNavigateToHome ) {
        Icon(
            painter = painterResource(R.drawable.baseline_clear_24),
            contentDescription = ""
        )
    }
})
}) {paddingValues ->
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(MaterialTheme.colorScheme.surface)) {
        Spacer(modifier = Modifier.height(4.dp))

        // Search Bar
        TextField(
            value = query.value,
            onValueChange = { searchViewModel.onSearch(it) },
            placeholder = { Text("Search movies...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            singleLine = true
        )

        // Movie List
        if(filteredMovies.value.isEmpty()) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(movies.value.size) { index ->
                    MovieItem(movie = movies.value[index],onNavigateToDetail )
                }

            }
        }
        else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredMovies.value.size) { index ->
                    MovieItem(movie = filteredMovies.value[index],onNavigateToDetail )
                }

            }
        }

    }
}

}


@Composable
fun MovieItem(movie: Movie,onNavigateToDetail: (Int) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().height(100.dp).background(MaterialTheme.colorScheme.surface).clickable {
        onNavigateToDetail(movie.id)
    }) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
            // Movie Image
            AsyncImage(
                model = BASE_IMAGE_URL + movie.image ,
                contentDescription = "",
                modifier = Modifier.aspectRatio(2/3f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Movie Details
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = movie.name, fontSize = 18.sp, maxLines = 1)
                Text(text = "Directed by ${movie.director}", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Year: ${movie.year}", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}