package com.example.matrixarctic.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    var name by remember { mutableStateOf("Player Name") }
    var isEditing by remember { mutableStateOf(false) }
    val health = 12

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.SentimentVerySatisfied,
            contentDescription = "Funny profile picture",
            modifier = Modifier.size(120.dp)
        )
        Spacer(Modifier.height(16.dp))

        if (isEditing) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Player Name") }
                )
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = { isEditing = false }) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save player name"
                    )
                }
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = { isEditing = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit player name"
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))
        Text("HEALTH", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Text("❤️ $health", style = MaterialTheme.typography.displaySmall)
    }
}
