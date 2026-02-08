package com.example.matrixarctic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.matrixarctic.ui.model.KnowledgeNote

@Composable
fun KnowledgeListScreen(
    navController: NavController,
    notes: SnapshotStateList<KnowledgeNote>,
    onDeleteNote: (KnowledgeNote) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<KnowledgeNote?>(null) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                noteToDelete = null
            },
            title = { Text("Удалить знание?") },
            confirmButton = {
                TextButton(onClick = {
                    noteToDelete?.let(onDeleteNote)
                    showDeleteDialog = false
                    noteToDelete = null
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    noteToDelete = null
                }) {
                    Text("No")
                }
            }
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(note.title, style = MaterialTheme.typography.titleMedium)
                            IconButton(onClick = {
                                noteToDelete = note
                                showDeleteDialog = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Удалить знание"
                                )
                            }
                        }
                        val preview = if (note.content.length > 50) note.content.take(50) + "…" else note.content
                        Text(preview)
                    }
                }
            }
        }
    }
}
