package com.example.waygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.waygo.navigation.NavGraph
import com.example.waygo.ui.theme.WayGoTheme
import com.example.waygo.utils.SessionManager
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Comprovem si l'usuari està logat al iniciar l'activitat
        val context = applicationContext
        val isLoggedIn = SessionManager.isLoggedIn(context)

        setContent {
            WayGoTheme {
                val navController = rememberNavController()

                // Comprovem l'estat de login
                LaunchedEffect(isLoggedIn) {
                    if (isLoggedIn) {
                        // Si l'usuari està logat, navega a la pantalla "home"
                        navController.navigate("home")
                    } else {
                        // Si no està logat, navega a la pantalla "login"
                        navController.navigate("login")
                    }
                }

                // Carreguem la NavGraph
                NavGraph(navController = navController)
            }
        }
    }
}
