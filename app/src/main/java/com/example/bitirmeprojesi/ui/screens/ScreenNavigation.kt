package com.example.bitirmeprojesi.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bitirmeprojesi.data.model.Movies
import com.google.gson.Gson

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainscreen") {
        composable(route = "mainscreen") {
            MainScreen(navController = navController)
        }
        composable(route = "detailscreen/{movie}", arguments = listOf(navArgument("movie"){
            type = NavType.StringType
        })) {
            val json = it.arguments?.getString("movie")
            val obje = Gson().fromJson(json, Movies::class.java)
            MovieDetailScreen(passedMovie = obje, navController = navController)
        }
        composable(route = "cartscreen") {
            CartScreen(navigationBack = {
                navController.navigate(route = "mainscreen")
            })
        }
    }
}