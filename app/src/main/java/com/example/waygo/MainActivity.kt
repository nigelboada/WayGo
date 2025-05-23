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
import androidx.compose.ui.platform.LocalContext
import com.example.waygo.utils.LanguageManager
import com.example.waygo.utils.UserPreferences

import android.util.Log



class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val prefs = UserPreferences(newBase)
        val langCode = prefs.getLanguage()  // default language
        val context = LanguageManager.setLocale(newBase, langCode)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val prefs = UserPreferences(this)
        val langCode = prefs.getLanguage()
        val localizedContext = LanguageManager.setLocale(this, langCode)

        setContent {
            CompositionLocalProvider(
                LocalContext provides localizedContext
            ) {
                WayGoTheme {
                    val navController = rememberNavController()

                    Log.d("MainActivity", "L'idioma carregat és: $langCode")

                    val isLoggedIn = SessionManager.isLoggedIn(localizedContext)
                    Log.d("MainActivity", "Usuari loguejat? $isLoggedIn")

                    // Directament passem el startDestination a la funció NavGraph
                    NavGraph(
                        navController = navController
//                        startDestination = if (isLoggedIn) "home" else "register"
                    )
                }
            }
        }
    }
}
