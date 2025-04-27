package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.home_title)) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(stringResource(R.string.home_welcome))

            Button(onClick = { navController.navigate("settings") }) {
                Text(stringResource(R.string.go_to_settings))
            }

            Button(onClick = { navController.navigate("profile") }) {
                Text(stringResource(R.string.go_to_profile))
            }

            Button(onClick = { navController.navigate("trip") }) {
                Text(stringResource(R.string.go_to_trips))
            }

            Button(onClick = { navController.navigate("about") }) {
                Text(stringResource(R.string.go_to_about))
            }

            Button(onClick = { navController.navigate("terms") }) {
                Text(stringResource(R.string.go_to_terms))
            }
        }
    }
}
