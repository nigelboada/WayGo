package com.example.waygo

import ConfigureProfileScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.waygo.ui.view.*
import com.example.waygo.ui.viewmodel.AuthViewModel

@Composable
fun NavGraph(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("home") { HomeScreen(navController, authViewModel) }
        composable("profile") { ProfileScreen(navController, authViewModel) }

        composable("profile/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val userId = it.arguments?.getInt("id") ?: -1
            ProfileScreen(navController, authViewModel)
        }

        composable("profileMenu") { ProfileScreen(navController, authViewModel) }
        composable("about") { AboutScreen(navController, authViewModel) }
        composable("termsAndConditions") { TermsScreen(navController, authViewModel) }
        composable("settings") { SettingsScreen(navController, authViewModel = authViewModel) }
        composable("configureProfile") { ConfigureProfileScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel = authViewModel, onRegisterSuccess = {
            navController.navigate("home") {
                popUpTo("register") { inclusive = true }
            }
            })

        }

        composable("itinerary/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Obtención del parámetro tripId desde los argumentos
            val tripIdString = backStackEntry.arguments?.getString("tripId")
            val tripId = tripIdString?.toIntOrNull() ?: -1
            //val tripId = 11;

            Log.d("Navigation", "TripId: $tripId")

            // Recuperar el tripName desde savedStateHandle
            val tripName = backStackEntry.savedStateHandle.get<String>("tripName") ?: "Trip"

            // Llamada a la pantalla ItineraryScreen
            ItineraryScreen(navController, tripId, tripName, authViewModel = authViewModel)
        }

        composable(
            route = "hotel/{hotelId}/{groupId}/{start}/{end}",
            arguments = listOf(
                navArgument("hotelId") { type = NavType.StringType },
                navArgument("groupId") { type = NavType.StringType },
                navArgument("start") { type = NavType.StringType },
                navArgument("end") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val hotelId = backStackEntry.arguments?.getString("hotelId") ?: ""
            val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
            val start = backStackEntry.arguments?.getString("start") ?: ""
            val end = backStackEntry.arguments?.getString("end") ?: ""

            HotelDetailScreen(
                hotelId = hotelId,
                groupId = groupId,
                start = start,
                end = end,
                navController = navController
            )
        }
    }
}
