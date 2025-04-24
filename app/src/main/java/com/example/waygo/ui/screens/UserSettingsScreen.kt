package com.example.waygo.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.MainActivity
import com.example.waygo.R
import com.example.waygo.utils.LanguageManager
import com.example.waygo.utils.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingsScreen(navController: NavController, context: Context) {
    val userPrefs = remember { UserPreferences(context) }

    val currentUsername = userPrefs.getUsername() ?: stringResource(R.string.default_user)
    var newPassword by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf(userPrefs.getLanguage()) }
    val availableLanguages = listOf("Català", "Español", "English")

    var expanded by remember { mutableStateOf(false) }

    val localContext = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.user_settings_title)) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "${stringResource(R.string.username_label)}: $currentUsername",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text(stringResource(R.string.new_password_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Selecció d’idioma
            Text(stringResource(R.string.language_label))
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

                    val languageMap = mapOf(
                        "Català" to "ca",
                        "Español" to "es",
                        "English" to "en"
                    )
                    val selectedLanguageCode = languageMap[selectedLanguage] ?: "en"

                    userPrefs.setLanguage(selectedLanguageCode) // Ex: "ca", "es", "en"
                    LanguageManager.setLocale(context, selectedLanguageCode)

                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    localContext.startActivity(intent)

                    val activity = LocalContext as Activity
                    activity.finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.save_changes))
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
                Text(stringResource(R.string.logout))
            }
        }
    }
}
