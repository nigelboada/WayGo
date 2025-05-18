package com.example.waygo.ui.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms & Conditions") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar enrere")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Benvingut/da a WayGo!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = """
                    Aquests són els termes i condicions d'ús de l'aplicació.

                    1. L'ús de l'app és responsabilitat de l'usuari.
                    2. Les dades no es comparteixen amb tercers.
                    3. Qualsevol ús malintencionat pot resultar en la suspensió del compte.
                    4. Aquest projecte és només per a ús educatiu.
                    
                    I altres termes legals...
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}