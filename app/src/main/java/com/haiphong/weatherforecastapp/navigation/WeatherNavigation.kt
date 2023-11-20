package com.haiphong.weatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haiphong.weatherforecastapp.ui.screens.main.MainScreen
import com.haiphong.weatherforecastapp.ui.screens.main.MainViewModel
import com.haiphong.weatherforecastapp.ui.screens.splash.SplashScreen

@Composable
fun WeatherNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val mainViewModel = viewModel<MainViewModel>()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name,
        modifier = modifier
    ) {
        composable(route = WeatherScreens.SplashScreen.name) {
            SplashScreen(toMainScreen = {
                navController.navigate(route = WeatherScreens.MainScreen.name)
            })
        }
        composable(route = WeatherScreens.MainScreen.name) {
            MainScreen(mainViewModel = mainViewModel)
        }
    }
}