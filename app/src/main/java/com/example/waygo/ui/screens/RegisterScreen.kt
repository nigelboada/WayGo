package com.example.waygo.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
    onRegisterClick: (name: String, email: String, password: String, address: String, country: String, phone: String, birthdate: String, login: String) -> Unit,
    onBackToLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registra't", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nom complet") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correu electrònic") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contrasenya") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())
        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Adreça") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = country, onValueChange = { country = it }, label = { Text("País") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Telèfon") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
        OutlinedTextField(value = birthdate, onValueChange = { birthdate = it }, label = { Text("Data de naixement (DD/MM/AAAA)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = login, onValueChange = { login = it }, label = { Text("Nom d’usuari") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onRegisterClick(name, email, password, address, country, phone, birthdate, login)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar-me")
        }

        TextButton(
            onClick = onBackToLoginClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Ja tens compte? Inicia sessió")
        }
    }
}

