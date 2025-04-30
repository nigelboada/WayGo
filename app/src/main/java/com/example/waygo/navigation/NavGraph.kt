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

import android.util.Log


@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {

    val itineraryViewModel: ActivityViewModel = viewModel()
    val tripViewModel: TripViewModel = viewModel()

    // Aquí definim el NavHost amb el paràmetre startDestination
    NavHost(navController = navController, startDestination = "register") {
        composable("login") {

            val context = LocalContext.current

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
            Log.d("NavGraph", "Navegant a RegisterScreen")
            RegisterScreen(navController) {
                navController.navigate("home")
            }
        }

        // Altres pantalles
        composable("home") {
            Log.d("NavGraph", "Navegant a HomeScreen")
            HomeScreen(navController)
        }
        composable("about") {
            Log.d("NavGraph", "Navegant a AboutScreen")
            AboutScreen(navController)
        }
        composable("profile") {
            Log.d("NavGraph", "Navegant a ProfileScreen")
            ProfileScreen(navController)
        }
        composable("terms") {
            Log.d("NavGraph", "Navegant a TermsScreen")
            TermsScreen(navController)
        }
        composable("trip") {
            Log.d("NavGraph", "Navegant a TripListScreen")
            TripListScreen(navController)
        }
        composable("add_trip") {
            Log.d("NavGraph", "Navegant a AddTripScreen")
            AddTripScreen(navController)
        }
        composable("edit_trip/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            Log.d("NavGraph", "Navegant a EditTripScreen")

            EditTripScreen(navController = navController, tripId = tripId)
        }
        composable("itinerary_list/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            Log.d("NavGraph", "Navegant a ActivityListScreen")

            ActivityListScreen(navController, tripId, itineraryViewModel)
        }
        composable("add_activity/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            Log.d("NavGraph", "Navegant a AddActivityScreen")

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

            Log.d("NavGraph", "Navegant a EditActivityScreen")
            EditActivityScreen(
                navController = navController,
                activityId = activityId,
                tripId = tripId,
                availableDays = availableDays,
                activityViewModel = itineraryViewModel
            )
        }
        composable("settings") {
            Log.d("NavGraph", "Navegant a UserSettingsScreen")
            UserSettingsScreen(navController = navController, context = LocalContext.current)
        }
    }
}

