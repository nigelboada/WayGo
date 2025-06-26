package com.example.waygo.ui.view

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.waygo.BuildConfig
import com.example.waygo.ui.search.SearchScreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// ------------------------ Navigation Destinations ---------------------------
sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Book     : Screen("book", Icons.Default.Search, "Book")
    object MyRes    : Screen("my_reservations", Icons.AutoMirrored.Filled.List, "My Reservations")
    object AllRes   : Screen("all_reservations", Icons.Default.Lock, "All Reservations")
    object Hotel    : Screen("hotel/{hotelId}/{groupId}/{start}/{end}", Icons.Default.Place, "Hotel") {
        fun create(hid: String, gid: String, s: String, e: String) = "hotel/$hid/$gid/$s/$e"
    }

}

val base = BuildConfig.HOTELS_API_URL.trimEnd('/')

@Composable
fun HomeHotel(rootNav: NavController) {

    /* tabs de la bottom-bar */
    val tabs = listOf(Screen.MyRes, Screen.AllRes, Screen.Book)



    /*  NavController exclusivo de los tabs  */
    val tabNav = rememberNavController()

    /* ---------- Scaffold con bottom-bar ---------- */
    Scaffold(
        bottomBar = {
            NavigationBar {
                val dest = tabNav.currentBackStackEntryAsState().value?.destination
                tabs.forEach { screen ->
                    NavigationBarItem(
                        selected = dest?.route == screen.route,
                        onClick = {
                            tabNav.navigate(screen.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(tabNav.graph.startDestinationId) {
                                    saveState = true          // conserva scroll, etc.
                                }
                            }
                        },
                        icon  = { Icon(screen.icon, screen.label) },
                        label = { Text(screen.label) }
                    )
                }
            }
        }
    ) { padding ->

        /* ---------- Contenido que cambia por tab ---------- */
        NavHost(
            navController = tabNav,
            startDestination = Screen.AllRes.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.AllRes.route) {

                AllReservationsScreen()
            }

            composable(Screen.Book.route) {
                SearchScreen(rootNav)        // usa el NavController raíz para ir a HotelDetail
            }

            composable(Screen.MyRes.route) {
                ReservationsScreen()
            }
        }
    }
}


@Composable
fun DateField(
    label: String,
    date: LocalDate?,
    onPick: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ISO_DATE

    OutlinedTextField(
        value = date?.format(formatter) ?: "",
        onValueChange = {},
        readOnly = true,
        enabled = false,                     // ← evita que consuma el click
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val now = date ?: LocalDate.now()
                DatePickerDialog(
                    context,
                    { _, y, m, d ->
                        onPick(LocalDate.of(y, m + 1, d))   // meses 0-based
                    },
                    now.year, now.monthValue - 1, now.dayOfMonth
                ).show()
            }
    )
}
