package com.example.matrixarctic.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KnowledgeDetailScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Код замка", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Text("Код замка №424. Получен и проверен.")
        Spacer(Modifier.weight(1f))
        Button(onClick = { /* navigate to QR */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Поделиться ")
        }
    }
}
