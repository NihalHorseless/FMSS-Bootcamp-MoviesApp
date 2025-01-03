package com.example.bitirmeprojesi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.ui.viewmodel.CartScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navigationBack: () -> Unit,
               cartScreenViewModel: CartScreenViewModel) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(navigationIcon = {
                IconButton(onClick = navigationBack) {
                    Icon(painter = painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "")
                }
            },
                title = {
                    Text(text = "Movie Cart")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(R.drawable.baseline_delete_24), contentDescription = "")
                    }
                }
            )
        }, bottomBar = {
            BottomAppBar() {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {

                    Button(onClick = {}, modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(text = "Order!")
                    }
                    Text(text = "1250 ₺", modifier = Modifier)
                }
            }
        }, snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
    { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(count = 5, itemContent = {
                Card(modifier = Modifier.padding(all = 5.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(painter = painterResource(R.drawable.django), contentDescription = "")

                        Column(modifier = Modifier.padding(all = 10.dp)) {
                            Text(text = "Django")
                            Text(text = "4.32 ☆☆☆☆")
                            Text(text = "250 ₺")


                        }
                        ButtonGroup()

                    }
                }
            })

        }

    }
}

@Composable
fun ButtonGroup() {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(onClick = {}, modifier = Modifier, shape = RectangleShape) {
            Icon(painter = painterResource(R.drawable.baseline_delete_24), contentDescription = "")
        }
        Button(onClick = {}, modifier = Modifier, shape = RectangleShape) {
            Text("2")
        }
        OutlinedButton(onClick = {}, modifier = Modifier, shape = RectangleShape) {
            Icon(painter = painterResource(R.drawable.baseline_add_24), contentDescription = "")
        }
    }
}

