package com.example.waygo.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {

    val auth = FirebaseAuth.getInstance() // FirebaseAuth instance

    var email by remember { mutableStateOf("") } // Declarem la variable email
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Inici de sessió") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = email,  // Utilitzem 'email' aquí
                onValueChange = { email = it },
                label = { Text("Correu electrònic") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contrasenya") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    // Realitzar l'autenticació amb Firebase Auth
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Inici de sessió correcte
                                    onLoginSuccess()
                                } else {
                                    // Error en iniciar sessió
                                    errorMessage = task.exception?.message
                                    Log.e("Login", "Error: ${task.exception?.message}")
                                }
                            }
                    } else {
                        errorMessage = "Completa tots els camps."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sessió")
            }

            TextButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("No tens compte? Registra't")
            }

        }
    }
}
