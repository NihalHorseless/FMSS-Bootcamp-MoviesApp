package com.example.bitirmeprojesi.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.retrofit.ApiUtils.Companion.BASE_IMAGE_URL
import com.example.bitirmeprojesi.ui.viewmodel.CartScreenViewModel
import com.example.bitirmeprojesi.util.convertToStar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navigationBack: () -> Unit,
    userName: String,
    cartScreenViewModel: CartScreenViewModel
) {
    val cartList = cartScreenViewModel.movieCartMovies.collectAsState(initial = listOf())
    val isCartEmpty by cartScreenViewModel.isCartEmpty.collectAsState()
    val totalPrice = cartScreenViewModel.totalCost.collectAsState(initial = 0)

    LaunchedEffect(Unit) {
        cartScreenViewModel.refreshCart(userName = "onur_aslan")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.tertiary),
                navigationIcon = {
                    IconButton(onClick = navigationBack) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = ""
                        )
                    }
                },
                title = { Text(text = "Movie Cart") },
                actions = {
                    IconButton(onClick = {
                        if (!isCartEmpty) {
                            cartScreenViewModel.deleteAllCarts(cartList.value)
                            Log.e("OnDeleteAll", cartList.value.toString())
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_delete_24),
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = MaterialTheme.colorScheme.tertiary) {
                Row(
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.tertiary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),onClick = {}, modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(text = "Order!")
                    }
                    Text(text = "${totalPrice.value} ₺", modifier = Modifier, color = Color.White)
                }
            }
        }
    ) { paddingValues ->
        if (isCartEmpty) {
            // Show empty state
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { Image(painter = painterResource(R.drawable.abandoned_cart), modifier = Modifier, contentDescription = "")
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your cart is empty",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.LightGray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                items(count = cartList.value.size) { index ->
                    val cartItem = cartList.value[index]
                    Card(modifier = Modifier.padding(all = 5.dp).background(MaterialTheme.colorScheme.surface)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AsyncImage(
                                model = BASE_IMAGE_URL + cartItem.image,
                                contentDescription = null
                            )
                            Column(modifier = Modifier.padding(all = 10.dp), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = cartItem.name, color = Color.White)
                                Text(text = "${cartItem.price} ₺", color = Color.White)
                            }
                            ButtonGroup(
                                onDelete = {
                                    cartScreenViewModel.decreaseAmount(movieCart = cartItem)
                                    Log.e("OnDelete", cartList.toString())
                                },
                                onIncrease = {
                                    cartScreenViewModel.increaseAmount(movieCart = cartItem)
                                    Log.e("OnIncrease", cartList.toString())
                                },
                                orderAmount = cartItem.orderAmount
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ButtonGroup(onDelete: () -> Unit, onIncrease: () -> Unit, orderAmount: Int) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(colors = ButtonDefaults.buttonColors(contentColor = Color.LightGray, containerColor = MaterialTheme.colorScheme.background),onClick = { onDelete() }, modifier = Modifier, shape = RectangleShape) {
            Icon(painter = painterResource(R.drawable.baseline_remove_24), contentDescription = "")
        }
        Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),onClick = {}, modifier = Modifier, shape = RectangleShape) {
            Text(text = "$orderAmount", color = Color.LightGray)
        }
        OutlinedButton(colors = ButtonDefaults.buttonColors(contentColor = Color.LightGray, containerColor = MaterialTheme.colorScheme.background),onClick = {
            onIncrease()
        }, modifier = Modifier, shape = RectangleShape) {
            Icon(painter = painterResource(R.drawable.baseline_add_24), contentDescription = "")
        }
    }
}

