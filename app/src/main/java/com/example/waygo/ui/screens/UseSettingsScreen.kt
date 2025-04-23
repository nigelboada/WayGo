package com.example.waygo.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.utils.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingsScreen(navController: NavController, context: Context) {
    val userPrefs = remember { UserPreferences(context) }

    val currentUsername = userPrefs.getUsername() ?: "Usuari desconegut"
    var newPassword by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf(userPrefs.getLanguage()) }
    val availableLanguages = listOf("Català", "Español", "English")

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Configuració d'usuari") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Nom d’usuari: $currentUsername", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("Nova contrasenya") },
                modifier = Modifier.fillMaxWidth()
            )

            // Selecció d’idioma
            Text("Idioma")
            Box {
                Button(onClick = { expanded = true }) {
                    Text(selectedLanguage)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    availableLanguages.forEach { lang ->
                        DropdownMenuItem(
                            text = { Text(lang) },
                            onClick = {
                                selectedLanguage = lang
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (newPassword.isNotBlank()) {
                        userPrefs.setPassword(newPassword)
                    }
                    userPrefs.setLanguage(selectedLanguage)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar canvis")
            }

            Button(
                onClick = {
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
