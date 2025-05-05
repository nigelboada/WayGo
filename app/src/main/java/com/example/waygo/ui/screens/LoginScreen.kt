package com.example.waygo.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.repository.AuthRepository
import com.example.waygo.utils.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val authRepository = remember { AuthRepository() }

    val context = LocalContext.current



    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Iniciar Sessió") })
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
                value = email,
                onValueChange = { email = it },
                label = { Text("Correu electrònic") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contrasenya") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
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
                    Log.d("LoginScreen", "Botó clicat")

                    authRepository.login(email, password) { success, result ->
                        Log.d("LoginScreen", "Callback rebut: success=$success, result=$result")

                        if (success) {
                            val userId = result ?: return@login
                            Log.d("LoginScreen", "Login correcte. ID: $userId")

                            SessionManager.saveLoginSession(context = context, userId = userId)
                            onLoginSuccess()
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            errorMessage = result
                            Log.e("LoginScreen", "Error de login: $result")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sessió")
            }


            TextButton(
                onClick = {
                    navController.navigate("register") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            ) {
                Text("Encara no tens compte? Registra't")
            }

            TextButton(
                onClick = { navController.navigate("forgot_password") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Has oblidat la contrasenya?")
            }

        }
    }
}
