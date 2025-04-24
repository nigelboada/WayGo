package com.example.waygo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.waygo.navigation.NavGraph
import com.example.waygo.ui.theme.WayGoTheme
import com.example.waygo.utils.SessionManager
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.waygo.utils.LanguageManager
import com.example.waygo.utils.UserPreferences

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val prefs = UserPreferences(newBase)
        val langCode = prefs.getLanguage() ?: "en"  // default language
        val context = LanguageManager.setLocale(newBase, langCode)
        super.attachBaseContext(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = UserPreferences(this)
        val langCode = prefs.getLanguage() ?: "en"
        val localizedContext = LanguageManager.setLocale(this, langCode)

        super.onCreate(savedInstanceState)

        setContent {
            // Usa CompositionLocalProvider per aplicar aquest context traduït
            CompositionLocalProvider(
                LocalContext provides localizedContext
            ) {
                WayGoTheme {
                    val navController = rememberNavController()
                    val isLoggedIn = SessionManager.isLoggedIn(localizedContext)

                    LaunchedEffect(isLoggedIn) {
                        if (isLoggedIn) {
                            navController.navigate("home")
                        } else {
                            navController.navigate("login")
                        }
                    }

                    NavGraph(navController = navController)
                }
            }
        }
    }
}
