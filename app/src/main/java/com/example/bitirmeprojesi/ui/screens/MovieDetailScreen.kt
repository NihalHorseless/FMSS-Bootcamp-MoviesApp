package com.example.bitirmeprojesi.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.bitirmeprojesi.data.model.Movies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieName: String, movieImage:String, moviePrice: Int,navigateToCart: () -> Unit) {
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text(text = movieName) }
        , actions = {
            IconButton(onClick =  {
                navigateToCart()
            }) {
                Icon(painter = painterResource(R.drawable.baseline_shopping_cart_24), contentDescription = "")
            }
        }) })
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val activity = (LocalContext.current as Activity)
            Image(
                bitmap = ImageBitmap.imageResource(
                    id = activity.resources.getIdentifier(
                        movieImage,
                        "drawable",
                        activity.packageName
                    )
                ), contentDescription = "", modifier = Modifier.size(200.dp, 300.dp)
            )
            Text(text = "${moviePrice} $", fontSize = 50.sp)
            Button(onClick = {}) {
                Text("Add to Cart")
            }

        }
    }
}