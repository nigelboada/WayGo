package com.example.waygo.ui.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.MainActivity
import com.example.waygo.utils.SessionManager
import com.example.waygo.utils.UserPreferences
import com.google.firebase.auth.FirebaseAuth

import android.util.Log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingsScreen(navController: NavController, context: Context) {
    val userPrefs = remember { UserPreferences(context) }
    val currentUsername = userPrefs.getUsername() ?: "Usuari"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Configuració de l'usuari") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Nom d'usuari (només mostrar-lo, no editar)
            Text(
                text = "Sessió iniciada com a: $currentUsername",
                style = MaterialTheme.typography.titleMedium
            )

            // Botó per tancar la sessió
            Button(
                onClick = {
                    Log.d("Settings", "L'usuari ha fet logout")

                    FirebaseAuth.getInstance().signOut()
                    SessionManager.saveLogoutTime(context) // ← Afegeix aquesta línia
                    SessionManager.clearSession(context)   // ← I aquesta
                    SessionManager.setLoggedIn(context, false)

                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Tanca la sessió")
            }


            // Opcional: reiniciar l'app (si es volgués carregar altres canvis)
            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reinicia aplicació")
            }
        }
    }
}
