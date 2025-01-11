package com.example.bitirmeprojesi.ui.screens

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.example.bitirmeprojesi.util.Splash

@Composable
fun ScreenNavigation(
    mainScreenViewModel: MainScreenViewModel,
    movieDetailScreenViewModel: MovieDetailScreenViewModel,
    cartScreenViewModel: CartScreenViewModel,
    favoriteScreenViewModel: FavoriteScreenViewModel,
    searchScreenViewModel: SearchScreenViewModel
) {
    val navController = rememberNavController()

    // New way of defining navigation
    NavHost(navController,
        startDestination = Splash,
        // Screen Transition Animations
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it } + fadeOut() }) {
        // Splash Screen
        composable<Splash> {
            SplashScreen (
                onAnimationEnd = {
                   navController.navigate(Home)
                }
            )
        }
        // Home Screen
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
        // Detail Screen
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
        // Cart Screen
        composable<Cart> {
            CartScreen(
                navigationBack = { navController.navigate(Home) },
                cartScreenViewModel = cartScreenViewModel
            )
        }
        // Favorites Screen
        composable<Favorites> {
            FavoritesScreen(navigateToHome = {
                navController.navigate(Home)
            }, onNavigateToDetail = { movieId ->
                navController.navigate(
                    Detail(
                        movieId = movieId
                    )
                )
            }, favoriteScreenViewModel = favoriteScreenViewModel)
        }
        // Search Screen
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