package com.example.testing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.testing.navigation.NavGraph
import com.example.testing.ui.theme.TestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestingTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
