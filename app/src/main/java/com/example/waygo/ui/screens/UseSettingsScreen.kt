package com.example.waygo.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.utils.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingsScreen(navController: NavController, context: Context) {
    val userPrefs = remember { UserPreferences(context) }

    var username by remember { mutableStateOf(userPrefs.getUsername() ?: "") }
    var isDarkMode by remember { mutableStateOf(userPrefs.isDarkModeEnabled()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuració d'usuari") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nom d'usuari") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Mode fosc", modifier = Modifier.weight(1f))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isDarkMode = it }
                )
            }

            Button(
                onClick = {
                    userPrefs.setUsername(username)
                    userPrefs.setDarkModeEnabled(isDarkMode)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }

            Button(
                onClick = {
                    // Es pot afegir una confirmació aquí
                    userPrefs.clear()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Tancar sessió")
            }
        }
    }
}
