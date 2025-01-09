package com.example.bitirmeprojesi.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bitirmeprojesi.ui.viewmodel.CartScreenViewModel
import com.example.bitirmeprojesi.ui.viewmodel.FavoriteScreenViewModel
import com.example.bitirmeprojesi.ui.viewmodel.MainScreenViewModel
import com.example.bitirmeprojesi.ui.viewmodel.MovieDetailScreenViewModel
import com.example.bitirmeprojesi.ui.viewmodel.SearchScreenViewModel
import com.example.bitirmeprojesi.util.Cart
import com.example.bitirmeprojesi.util.Detail
import com.example.bitirmeprojesi.util.Favorites
import com.example.bitirmeprojesi.util.Home
import com.example.bitirmeprojesi.util.Search

@Composable
fun ScreenNavigation(
    mainScreenViewModel: MainScreenViewModel,
    movieDetailScreenViewModel: MovieDetailScreenViewModel,
    cartScreenViewModel: CartScreenViewModel,
    favoriteScreenViewModel: FavoriteScreenViewModel,
    searchScreenViewModel: SearchScreenViewModel
) {
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
            MainScreen(onNavigateToDetail = { movieId ->
                navController.navigate(
                    Detail(
                        movieId = movieId
                    )
                )
            }, onNavigateToCart = { userName ->
                navController.navigate(Cart(userName = userName))
            }, onNavigateToFavs = {
                navController.navigate(Favorites)
            }, onNavigateToSearch = {
                navController.navigate(Search)
            },
                mainScreenViewModel = mainScreenViewModel
            )
        }
        composable<Detail> { backStackEntry ->
            val detail: Detail = backStackEntry.toRoute()
            MovieDetailScreen(
                movieId = detail.movieId,
                navigateToCart = { userName ->
                    navController.navigate(
                        Cart(
                            userName = userName
                        )
                    )
                }, navigateBack = {
                    navController.popBackStack()
                }, movieDetailScreenViewModel = movieDetailScreenViewModel
            )
        }
        composable<Cart> { backStackEntry ->
            val cart: Cart = backStackEntry.toRoute()
            CartScreen(
                navigationBack = { navController.navigate(Home) },
                cartScreenViewModel = cartScreenViewModel,
                userName = cart.userName
            )
        }
        composable<Favorites> {
            FavoritesScreen(navigateToHome = {
                navController.navigate(Home)
            }, onNavigateToDetail = { movieId ->
                navController.navigate(
                    Detail(
                        movieId = movieId
                    )
                )
            }, onNavigateToCart = { userName ->
                navController.navigate(Cart(userName = userName))
            }, favoriteScreenViewModel = favoriteScreenViewModel)
        }
        composable<Search> {
            MovieSearchScreen(onNavigateToDetail = {
                    movieId ->
                navController.navigate(
                    Detail(
                        movieId = movieId
                    )
                )
            },onNavigateToHome = {
                navController.navigate(Home)
            },searchViewModel = searchScreenViewModel)
        }
    }

}