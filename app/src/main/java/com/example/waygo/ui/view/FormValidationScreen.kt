package com.example.waygo.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.ui.viewmodel.FormValidationViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormValidationScreen(
    navController: NavController,
    viewModel: FormValidationViewModel = hiltViewModel()
) {
    // Leemos los errores
    val emailError = viewModel.emailError
    val passwordError = viewModel.passwordError
    val confirmPasswordError = viewModel.confirmPasswordError
    val storeNameError = viewModel.storeNameError
    var context = LocalContext.current
//    val storeLocationError = viewModel.storeLocationError
//    val mobileError = viewModel.mobileError
//    val pinCodeError = viewModel.pinCodeError

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Campo: Email
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Email") },
                isError = emailError != null,
                modifier = Modifier.fillMaxWidth()
            )
            emailError?.let { errorText ->
                Text(
                    text = errorText,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Campo: Password
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Password") },
                isError = passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            passwordError?.let { errorText ->
                Text(
                    text = errorText,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Campo: Confirm Password
            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                label = { Text("Confirm Password") },
                isError = confirmPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            confirmPasswordError?.let { errorText ->
                Text(
                    text = errorText,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Campo: Store Name
            OutlinedTextField(
                value = viewModel.storeName,
                onValueChange = { viewModel.onStoreNameChanged(it) },
                label = { Text("Store Name") },
                isError = storeNameError != null,
                modifier = Modifier.fillMaxWidth()
            )
            storeNameError?.let { errorText ->
                Text(
                    text = errorText,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }



            // Botón de Registrar
            Button(
                onClick = {
                    // Llamada al ViewModel
                    if(viewModel.onRegisterClicked() == true){

                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Registrar")
            }
        }
    }
}