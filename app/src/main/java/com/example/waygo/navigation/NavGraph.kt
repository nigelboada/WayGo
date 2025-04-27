package com.example.waygo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.waygo.ui.screens.*
import com.example.waygo.utils.SessionManager
import com.example.waygo.viewmodel.ActivityViewModel
import com.example.waygo.viewmodel.TripViewModel

@Composable

fun NavGraph(navController: NavHostController, startDestination: String) {
    val context = LocalContext.current

    val itineraryViewModel: ActivityViewModel = viewModel()
    val tripViewModel: TripViewModel = viewModel()

    // Aquí definim el NavHost amb el paràmetre startDestination
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    SessionManager.setLoggedIn(context, true)
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            RegisterScreen(navController) {
                navController.navigate("home")
            }
        }

        // Altres pantalles
        composable("home") { HomeScreen(navController) }
        composable("about") { AboutScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("terms") { TermsScreen(navController) }
        composable("trip") { TripListScreen(navController) }
        composable("add_trip") { AddTripScreen(navController) }
        composable("edit_trip/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            EditTripScreen(navController = navController, tripId = tripId)
        }
        composable("itinerary_list/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            ActivityListScreen(navController, tripId, itineraryViewModel)
        }
        composable("add_activity/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            AddActivityScreen(navController, tripId, itineraryViewModel)
        }
        composable(
            "edit_activity/{activityId}/{tripId}",
            arguments = listOf(
                navArgument("activityId") { type = NavType.StringType },
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId") ?: ""
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            val availableDays = tripViewModel.getDaysForTrip(tripId)

            EditActivityScreen(
                navController = navController,
                activityId = activityId,
                tripId = tripId,
                availableDays = availableDays,
                activityViewModel = itineraryViewModel
            )
        }
        composable("settings") {
            UserSettingsScreen(navController = navController, context = LocalContext.current)
        }
    }
}

