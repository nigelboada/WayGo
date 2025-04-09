package com.example.waygo.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.utils.SessionManager
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit  // Afegeix el paràmetre per gestionar l'inici de sessió correcte
) {
    val context = LocalContext.current  // Obtenim el context aquí

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Login") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Login Screen")
            // Afegir lògica de Login (simulada en aquest cas)
            Button(onClick = {
                // Aquí normalment faries la verificació d'usuaris, però per simular-ho:
                SessionManager.setLoggedIn(context, true)  // Marquem que l'usuari s'ha loguejat
                onLoginSuccess()  // Truquem a l'acció de Login exitós
                navController.navigate("home")  // A continuació, anem a la pantalla principal
            }) {
                Text("Login")
            }
        }
    }
}
