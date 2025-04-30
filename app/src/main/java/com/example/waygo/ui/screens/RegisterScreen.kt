package com.example.waygo.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// Aquesta funció envia el correu de verificació
private fun sendEmailVerification() {
    val user = Firebase.auth.currentUser
    user?.sendEmailVerification()?.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Auth", "Verification email sent.")
        }
    }
}

// Funció de registre d'usuari
fun signup(email: String, password: String, navController: NavController) {
    if (email.isEmpty() || password.isEmpty()) {
        Log.e("Auth", "Email or password can't be empty")
        return
    }

    Firebase.auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendEmailVerification()
                navController.navigate("login") // Redirigeix a la pantalla de login
            } else {
                Log.e("Auth", task.exception?.message ?: "Something went wrong")
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.register_title)) })
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
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password_label)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(stringResource(R.string.confirm_password_label)) },
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
                    if (username.isNotBlank() && password.isNotBlank() && password == confirmPassword) {
                        signup(username, password, navController)
                    } else {
                        errorMessage = "Please make sure all fields are filled correctly."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.register_button))
            }
        }
    }
}
