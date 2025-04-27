package com.example.waygo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.waygo.ui.screens.LoginScreen
import com.example.waygo.ui.theme.WayGoTheme
import com.example.waygo.utils.SessionManager

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cridar setContent dins d'una funció composable
        setContent {
            // Utilitzant directament el bloc composable aquí
            LoginScreenContent()
        }
    }
}

@Composable
fun LoginScreenContent() {
    val navController = rememberNavController()
    val context = LocalContext.current  // Obtenir el context actual

    WayGoTheme {
        // Passant el context i altres paràmetres
        LoginScreen(
            navController = navController,
            onLoginSuccess = {
                // Quan el login sigui correcte, es marca com logat i es redirigeix
                SessionManager.setLoggedIn(context, true)
                // Passant el context a l'Intent
                context.startActivity(Intent(context, MainActivity::class.java))
            }
        )
    }
}