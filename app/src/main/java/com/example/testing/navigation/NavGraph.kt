package com.example.testing.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testing.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("about") { AboutScreen(navController) }
        composable("itinerary") { ItineraryScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("trip") { TripScreen(navController) }
        composable("version") { VersionScreen(navController) }
    }
}
