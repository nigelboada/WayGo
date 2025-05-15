package com.example.waygo

import HotelListScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.waygo.ui.view.*
import com.example.waygo.utils.SessionManager
import com.example.waygo.ui.viewmodel.ActivityViewModel
import com.example.waygo.ui.viewmodel.TripViewModel

import android.util.Log
import androidx.compose.runtime.remember
import com.example.waygo.data.local.AppDatabase
import com.example.waygo.domain.repository.HotelRepository
import com.example.waygo.domain.repository.TripRepository
import com.example.waygo.ui.viewmodel.HotelViewModel
import com.example.waygo.ui.viewmodel.HotelViewModelFactory
import com.example.waygo.ui.viewmodel.TripViewModelFactory


@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val tripDao = db.tripDao()  // Aquí obtenim el tripDao directament

    val tripRepository = TripRepository(tripDao)

    val tripViewModel: TripViewModel = viewModel(
        factory = TripViewModelFactory(tripRepository)
    )

    val itineraryViewModel: ActivityViewModel = viewModel() // Afegeix aquest ViewModel

    val isLoggedIn = remember { SessionManager.isLoggedIn(context) }
    val startDestination = if (isLoggedIn) "home" else "register"

    // Aquí definim el NavHost amb el paràmetre startDestination
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            val context = LocalContext.current

            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    SessionManager.setLoggedIn(context, true)
                    Log.d("LoginScreen", "S'ha guardat que l'usuari està loguejat")
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

        composable("forgot_password") {
            ForgotPasswordScreen(navController)
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
            TripListScreen(navController, tripViewModel)
        }
        composable("add_trip") {
            Log.d("NavGraph", "Navegant a AddTripScreen")
            AddTripScreen(navController = navController, viewModel = tripViewModel)
        }
        composable("edit_trip/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: return@composable
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val tripRepository = TripRepository(db.tripDao())
            val tripViewModel: TripViewModel = viewModel(factory = TripViewModelFactory(tripRepository))

            EditTripScreen(navController = navController, tripId = tripId, viewModel = tripViewModel)
        }

        composable("itinerary_list/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            Log.d("NavGraph", "Navegant a ActivityListScreen")

            ActivityListScreen(
                tripId = tripId,
                navController = navController,
                tripViewModel = tripViewModel,
                activityViewModel = itineraryViewModel
            )

        }

        composable("add_activity/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            Log.d("NavGraph", "Navegant a AddActivityScreen")

            AddActivityScreen(
                navController = navController,
                tripId = tripId,
                activityViewModel = itineraryViewModel,
                tripViewModel = tripViewModel
            )
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

        composable("activity_list/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            ActivityListScreen(
                tripId = tripId,
                navController = navController,
                tripViewModel = tripViewModel,
                activityViewModel = itineraryViewModel
            )
        }


        composable("hotel_list") {
            val hotelRepository = HotelRepository() // o passa-li dependències si cal
            val hotelViewModel: HotelViewModel = viewModel(
                factory = HotelViewModelFactory(hotelRepository)
            )

            HotelListScreen(viewModel = hotelViewModel)
        }




    }
}
