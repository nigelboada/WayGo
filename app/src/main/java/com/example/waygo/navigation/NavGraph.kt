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
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current  // Obtenim el context actual

    val itineraryViewModel: ActivityViewModel = viewModel()
    val tripViewModel: TripViewModel = viewModel()



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
            EditTripScreen(navController = navController, tripId = tripId)
        }


        composable("itinerary_list/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            ActivityListScreen(navController, tripId, itineraryViewModel)
        }

        composable("add_activity/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            AddActivityScreen(navController, tripId, itineraryViewModel) // <-- Aquí el compartim
        }

        composable("edit_activity/{activityId}/{tripId}",
            arguments = listOf(
                navArgument("activityId") { type = NavType.StringType },
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId") ?: ""
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""

            // Aquí recuperem la llista de dies del viatge (availableDays) associada al tripId
            val availableDays = tripViewModel.getDaysForTrip(tripId)  // Aquí has de tenir una funció que retorni la llista de dies per aquest tripId

            EditActivityScreen(
                navController = navController,
                activityId = activityId,
                tripId = tripId,
                availableDays = availableDays,  // Afegim la llista de dies
                activityViewModel = itineraryViewModel
            )
        }





    }
}
