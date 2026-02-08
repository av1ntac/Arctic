package com.example.matrixarctic.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.matrixarctic.ui.model.KnowledgeNote
import com.example.matrixarctic.ui.screens.KnowledgeCopyEditScreen
import com.example.matrixarctic.ui.screens.KnowledgeDetailScreen
import com.example.matrixarctic.ui.screens.KnowledgeListScreen
import com.example.matrixarctic.ui.screens.ProfileScreen
import com.example.matrixarctic.ui.screens.ScanScreen
import com.example.matrixarctic.ui.screens.ShowQrScreen
import java.util.UUID

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Profile : Screen("profile", Icons.Default.Person, "Профиль")
    object Knowledge : Screen("knowledge", Icons.Default.Info, "Знания")
    object Scan : Screen("scan", Icons.Default.Star, "QR")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val items = listOf(Screen.Profile, Screen.Knowledge, Screen.Scan)
    val notes = remember {
        mutableStateListOf(
            KnowledgeNote("1", "Код замка", "Код замка №...")
        )
    }

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
            composable(Screen.Knowledge.route) { KnowledgeListScreen(navController, notes) }
            composable("knowledge/{id}") { backStackEntry ->
                val knowledgeId = backStackEntry.arguments?.getString("id")
                val note = notes.firstOrNull { it.id == knowledgeId }
                KnowledgeDetailScreen(navController, note)
            }
            composable("knowledge/{id}/copy") { backStackEntry ->
                val knowledgeId = backStackEntry.arguments?.getString("id")
                val sourceNote = notes.firstOrNull { it.id == knowledgeId }

                if (sourceNote != null) {
                    KnowledgeCopyEditScreen(
                        sourceNote = sourceNote,
                        onSave = { editedContent ->
                            notes.add(
                                KnowledgeNote(
                                    id = UUID.randomUUID().toString(),
                                    title = sourceNote.title,
                                    content = editedContent
                                )
                            )
                            navController.navigate(Screen.Knowledge.route) {
                                popUpTo(Screen.Knowledge.route)
                                launchSingleTop = true
                            }
                        },
                        onCancel = { navController.popBackStack() }
                    )
                } else {
                    Text("Знание не найдено")
                }
            }
            composable(Screen.Scan.route) { ScanScreen() }
            composable("show_qr/{id}") { ShowQrScreen() }
        }
    }
}
