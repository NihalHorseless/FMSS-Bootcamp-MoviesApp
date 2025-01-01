package com.example.bitirmeprojesi.ui.screens

import com.example.bitirmeprojesi.data.model.Movies

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bitirmeprojesi.R
import com.google.gson.Gson


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var movieList = remember { mutableStateListOf<Movies>() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        val f1 = Movies(1, "Django", "django", 24)
        movieList.add(f1)
    }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text(text = "Movies") }
    , actions = {
        IconButton(onClick =  {
            navController.navigate("cartscreen")
        }) {
            Icon(painter = painterResource(R.drawable.baseline_shopping_cart_24), contentDescription = "")
        }
        }) })
    { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2)
        ) {
            items(
                count = movieList.count(),
                itemContent = {
                    val movie = movieList[it]
                    Card(modifier = Modifier.padding(all = 5.dp)) {
                        Column(modifier = Modifier.fillMaxWidth().clickable {
                            val movieJson = Gson().toJson(movie)
                            navController.navigate("detailscreen/${movieJson}")
                        }) {
                            val activity = (LocalContext.current as Activity)
                            Image(
                                bitmap = ImageBitmap.imageResource(
                                    id = activity.resources.getIdentifier(
                                        movie.image,
                                        "drawable",
                                        activity.packageName
                                    )
                                ), contentDescription = "", modifier = Modifier.size(200.dp, 300.dp)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(text = "${movie.price} $", fontSize = 25.sp)
                                Button(onClick = {

                                }
                                ) {
                                    Text(text = "Add to Cart")
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}