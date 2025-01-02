package com.example.bitirmeprojesi.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.bitirmeprojesi.data.model.Movies
import com.example.bitirmeprojesi.util.Cart
import com.example.bitirmeprojesi.util.Detail
import com.example.bitirmeprojesi.util.Home

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()
 // Old Way
 //   NavHost(navController = navController, startDestination = "mainscreen") {
 //       composable(route = "mainscreen") {
 //           MainScreen(navController = navController)
 //       }
 //       composable(route = "detailscreen/{movie}", arguments = listOf(navArgument("movie") {
 //           type = NavType.StringType
 //       })) {
 //           val json = it.arguments?.getString("movie")
 //           val obje = Gson().fromJson(json, Movies::class.java)
 //           MovieDetailScreen(passedMovie = obje, navController = navController)
 //       }
 //       composable(route = "cartscreen") {
 //           CartScreen(navigationBack = {
 //               navController.navigate(route = "mainscreen")
 //           })
 //       }
 //   }

    // New Way
    NavHost(navController, startDestination = Home) {
        composable<Home> {
            MainScreen(onNavigateToDetail = { movieName,moviePrice,movieImage ->
                navController.navigate(Detail(movieName = movieName, moviePrice = moviePrice, movieImage = movieImage))
            }, onNavigateToCart = {
                navController.navigate(Cart)
            })
        }
        composable<Detail> { backStackEntry ->
            val detail: Detail = backStackEntry.toRoute()
            MovieDetailScreen(movieName = detail.movieName, moviePrice = detail.moviePrice, movieImage = detail.movieImage, navigateToCart = {
                navController.navigate(Cart)
            })
        }
        composable<Cart> {
            CartScreen(navigationBack = { navController.navigate(Home) })
        }
    }

}