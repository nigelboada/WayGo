package com.example.waygo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.waygo.ui.screens.*
import com.example.waygo.utils.SessionManager

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current  // Obtenim el context actual

    NavHost(navController = navController, startDestination = "home") {


        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    // Quan el login sigui correcte, es marca com logat i es redirigeix
                    SessionManager.setLoggedIn(context, true)  // Es passa el context aquí
                    navController.navigate("home")
                }
            )
        }

        composable("home") { HomeScreen(navController) }
        composable("about") { AboutScreen(navController) }

        composable("profile") { ProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("terms") { TermsScreen(navController) }

        composable("trip") { TripListScreen(navController) }
        composable("add_trip") { AddTripScreen(navController) }
        composable("edit_trip/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            EditTripScreen(navController, tripId)
        }

        composable("itinerary/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            ItineraryScreen(navController, tripId)
        }

        composable("add_itinerary/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            AddItineraryItemScreen(navController, tripId)
        }

        composable("edit_activity/{tripId}/{activityId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            val activityId = backStackEntry.arguments?.getString("activityId") ?: ""
            EditItineraryItemScreen(navController, tripId, activityId)
        }





    }
}
