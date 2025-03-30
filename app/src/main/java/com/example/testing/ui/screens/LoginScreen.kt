package com.example.testing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testing.R
import com.example.testing.utils.LoginUtils

@Composable
fun LoginScreen(navController: NavController) {

    // States for username, password, and the alert dialog
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    val loginUtils: LoginUtils = LoginUtils()

    // Get default values from strings.xml
    val defaultUser = stringResource(id = R.string.default_user)
    val defaultPass = stringResource(id = R.string.default_pass)

    //providing values to jump without type
    username = defaultUser
    password = defaultPass

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (loginUtils.isValidEmailAddress(defaultUser) &&
                    //loginUtils.isValidPassword(defaultPass) &&
                    username == defaultUser && password == defaultPass

                    ) {
                    // Navigate to Home and remove Login from the back stack
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    showAlert = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }

    // Show an alert dialog if the login fails
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Login Failed") },
            text = { Text("Invalid username or password.") },
            confirmButton = {
                Button(onClick = { showAlert = false }) {
                    Text("OK")
                }
            }
        )
    }
}
