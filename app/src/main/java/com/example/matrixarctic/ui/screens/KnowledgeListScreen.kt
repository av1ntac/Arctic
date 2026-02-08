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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                            IconButton(onClick = { onDeleteNote(note) }) {
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
