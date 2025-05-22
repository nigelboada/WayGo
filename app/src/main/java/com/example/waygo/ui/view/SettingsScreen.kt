package com.example.waygo.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.waygo.R
import com.example.waygo.ui.viewmodel.AuthViewModel
import com.example.waygo.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController,
                   viewModel: SettingsViewModel = hiltViewModel(),
                   authViewModel: AuthViewModel
) {
    val isDarkTheme by remember { mutableStateOf(viewModel.isDarkTheme) }
    val language by remember { mutableStateOf(viewModel.language) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.settings), color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsOption(stringResource(id = R.string.about)) { navController.navigate("about") }
            SettingsOption(stringResource(id = R.string.terms_conditions)) { navController.navigate("termsAndConditions") }
            SettingsOption(stringResource(id = R.string.configure_profile)) { navController.navigate("configureProfile") }

            LanguageDropdown(
                selectedLanguage = language,
                onLanguageSelected = { newLang -> viewModel.updateLanguage(newLang) },
                availableLanguages = listOf("en", "es", "ca")
            )

            SettingsToggleSwitch(
                title = stringResource(id = R.string.dark_mode),
                isChecked = isDarkTheme,
                onCheckedChange = { viewModel.updateDarkTheme(it) }
            )

            SettingsOption(title = stringResource(id = R.string.logout)) {
                authViewModel.signout()
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun SettingsOption(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Arrow", tint = Color.Gray)
    }
    HorizontalDivider(color = Color(0xFFE0E0E0))
}

@Composable
fun SettingsToggleSwitch(title: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF007AFF),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFB0B0B0)
            )
        )
    }
    HorizontalDivider(color = Color(0xFFE0E0E0))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdown(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    availableLanguages: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val languageDisplay = when (selectedLanguage) {
        "es" -> stringResource(id = R.string.spanish)
        "en" -> stringResource(id = R.string.english)
        "ca" -> stringResource(id = R.string.catalan)
        else -> selectedLanguage
    }

    Column {
        Text(stringResource(id = R.string.language), fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        OutlinedTextField(
            value = languageDisplay,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand Language Menu", tint = Color.Black)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedPlaceholderColor = Color.Black,
                unfocusedPlaceholderColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp)),
        ) {
            availableLanguages.forEach { lang ->
                val langName = when (lang) {
                    "es" -> stringResource(id = R.string.spanish)
                    "en" -> stringResource(id = R.string.english)
                    "ca" -> stringResource(id = R.string.catalan)
                    else -> lang
                }
                DropdownMenuItem(
                    text = { Text(langName, color = Color.Black) },
                    onClick = {
                        onLanguageSelected(lang)
                        expanded = false
                    }, modifier = Modifier
                        .background(Color.White)
                        .padding(4.dp)
                )
            }
        }
    }
}



            // Botó per tancar la sessió
//            Button(
//                onClick = {
//                    Log.d("Settings", "L'usuari ha fet logout")
//
//                    FirebaseAuth.getInstance().signOut()
//                    SessionManager.saveLogoutTime(context) // ← Afegeix aquesta línia
//                    SessionManager.clearSession(context)   // ← I aquesta
//                    SessionManager.setLoggedIn(context, false)
//
//                    navController.navigate("login") {
//                        popUpTo("home") { inclusive = true }
//                        launchSingleTop = true
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.error
//                )
//            ) {
//                Text("Tanca la sessió")
//            }

