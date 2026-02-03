package com.example.matrixarctic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.matrixarctic.ui.model.KnowledgeNote

@Composable
fun KnowledgeListScreen(navController: NavController) {
    val notes = remember {
        mutableStateListOf(
            KnowledgeNote("1", "Ancient Code", "Long ancient text...")
        )
    }

    if (notes.isEmpty()) {
        EmptyKnowledgeState(navController)
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("knowledge/${note.id}") }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.content.take(50) + "…")
                    }
                }
            }
        }
    }
}
