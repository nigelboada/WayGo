package com.example.waygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.example.waygo.ui.theme.WayGoTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.lifecycleScope
import com.example.waygo.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authViewModel : AuthViewModel by viewModels()
        val isChecking = mutableStateOf(true)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isChecking.value
            }
        }

        lifecycleScope.launch {
            delay(1500L)
            isChecking.value = false
        }

        setContent {
            WayGoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen(authViewModel = authViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavGraph(navController = navController, authViewModel)
}


