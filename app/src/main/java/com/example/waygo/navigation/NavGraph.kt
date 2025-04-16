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

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current  // Obtenim el context actual

    val itineraryViewModel: ActivityViewModel = viewModel()




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


        composable("itinerary_list/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            ActivityListScreen(navController, tripId, itineraryViewModel)
        }

        composable("add_activity/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            AddActivityScreen(navController, tripId, itineraryViewModel) // <-- Aquí el compartim
        }
        composable(
            route = "edit_activity/{activityId}/{tripId}",
            arguments = listOf(
                navArgument("activityId") { type = NavType.StringType },
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId") ?: ""
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            EditActivityScreen(navController, activityId, tripId, itineraryViewModel)
        }






    }
}
