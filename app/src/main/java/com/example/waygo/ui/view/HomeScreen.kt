package com.example.waygo.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.ui.viewmodel.AuthViewModel


enum class PlanningMode {
    EXPLORE, SEARCH, PROFILE
}

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel) {
    var selectedMode by remember { mutableStateOf(PlanningMode.PROFILE) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedMode) { selectedMode = it }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            when (selectedMode) {
                PlanningMode.EXPLORE -> GlobeScreen()
                PlanningMode.SEARCH -> SearchScreen(navController = navController)
                PlanningMode.PROFILE -> ProfileScreen(navController, authViewModel)

            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedMode: PlanningMode, onModeSelected: (PlanningMode) -> Unit) {
    NavigationBar (containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE0E0E0), RectangleShape)
    ){
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.LocationOn, contentDescription = "Explore", tint = Color.Black) },
            label = { Text(stringResource(id = R.string.explore), color = Color.Black) },
            selected = selectedMode == PlanningMode.EXPLORE,
            onClick = { onModeSelected(PlanningMode.EXPLORE) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.LightGray.copy(alpha = 0.2f)
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Black) },
            label = { Text(stringResource(id = R.string.search), color = Color.Black) },
            selected = selectedMode == PlanningMode.SEARCH,
            onClick = { onModeSelected(PlanningMode.SEARCH) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.LightGray.copy(alpha = 0.2f)
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.Black) },
            label = { Text(stringResource(id = R.string.profile), color = Color.Black) },
            selected = selectedMode == PlanningMode.PROFILE,
            onClick = { onModeSelected(PlanningMode.PROFILE) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.LightGray.copy(alpha = 0.2f)
            )
        )
    }
}