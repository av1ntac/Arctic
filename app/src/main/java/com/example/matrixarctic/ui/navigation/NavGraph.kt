package com.example.matrixarctic.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.matrixarctic.ui.screens.*

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Profile : Screen("profile", Icons.Default.Person, "Профиль")
    object Knowledge : Screen("knowledge", Icons.Default.Info, "Знания")
    object Scan : Screen("scan", Icons.Default.Star, "QR")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val items = listOf(Screen.Profile, Screen.Knowledge, Screen.Scan)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach {
                    NavigationBarItem(
                        selected = currentRoute == it.route,
                        onClick = { navController.navigate(it.route) },
                        icon = { Icon(it.icon, null) },
                        label = { Text(it.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Profile.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.Knowledge.route) { KnowledgeListScreen(navController) }
            composable("knowledge/{id}") { KnowledgeDetailScreen() }
            composable(Screen.Scan.route) { ScanScreen() }
            composable("show_qr/{id}") { ShowQrScreen() }
        }
    }
}
