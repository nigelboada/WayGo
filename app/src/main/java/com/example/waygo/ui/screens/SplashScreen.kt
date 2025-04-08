package com.example.waygo.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.waygo.MainActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mostrem la splash screen
        installSplashScreen()

        // Anem a la MainActivity després d'un petit delay
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Tanquem aquesta activitat perquè no es pugui tornar enrere
    }
}
