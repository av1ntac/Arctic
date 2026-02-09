package com.example.matrixarctic.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matrixarctic.ui.model.KnowledgeNote

private const val MAX_KNOWLEDGE_TEXT_LENGTH = 1000

@Composable
fun KnowledgeCopyEditScreen(
    sourceNote: KnowledgeNote,
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var content by remember(sourceNote.id) { mutableStateOf(sourceNote.content.take(MAX_KNOWLEDGE_TEXT_LENGTH)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Редактирование копии")

        OutlinedTextField(
            value = sourceNote.title,
            onValueChange = {},
            enabled = false,
            label = { Text("Заголовок") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = content,
            onValueChange = { updatedText ->
                content = updatedText.take(MAX_KNOWLEDGE_TEXT_LENGTH)
            },
            label = { Text("Текст знания") },
            supportingText = { Text("${content.length}/$MAX_KNOWLEDGE_TEXT_LENGTH") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onSave(content.take(MAX_KNOWLEDGE_TEXT_LENGTH)) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
        }
    }
}
