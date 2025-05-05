package com.example.waygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.waygo.navigation.NavGraph
import com.example.waygo.utils.SessionManager
import com.example.waygo.ui.theme.WayGoTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WayGoTheme {
                SplashScreen()
            }
        }
    }
}

@Composable
fun SplashScreen() {

    val context = LocalContext.current  // Obtenim el context dins d’un @Composable

    // NavController per la navegació
    val navController = rememberNavController()

    // Verifiquem si l'usuari ja està registrat
    val isLoggedIn = remember { mutableStateOf(SessionManager.isLoggedIn(context)) }

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("register") {
            popUpTo("splash") { inclusive = true }
        }
    }


    // A continuació, passarem la nostra navController i la startDestination
    NavGraph(navController = navController)
}
