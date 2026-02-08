package com.example.matrixarctic.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.matrixarctic.ui.model.KnowledgeNote
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun KnowledgeDetailScreen(
    navController: NavController,
    note: KnowledgeNote?
) {
    val qrSizePx = with(LocalDensity.current) { 240.dp.roundToPx() }
    var qrImage by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    var showCopyDialog by remember { mutableStateOf(false) }

    if (showCopyDialog) {
        AlertDialog(
            onDismissRequest = { showCopyDialog = false },
            title = { Text("Скопировать знание?") },
            confirmButton = {
                TextButton(onClick = {
                    showCopyDialog = false
                    note?.let { navController.navigate("knowledge/${it.id}/copy") }
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCopyDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(note?.title ?: "Код замка", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Text(note?.content ?: "Код замка №424. Получен и проверен.")
        Spacer(Modifier.weight(1f))
        qrImage?.let { image ->
            Image(
                bitmap = image,
                contentDescription = "QR code",
                modifier = Modifier
                    .size(240.dp)
                    .clickable { qrImage = null }
            )
            Spacer(Modifier.height(16.dp))
        }
        Button(
            onClick = { showCopyDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Копировать")
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                qrImage = generateQrCode(KNOWLEDGE_QR_CONTENT, qrSizePx).asImageBitmap()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поделиться ")
        }
    }
}

private const val KNOWLEDGE_GUID = "2f05c48c-3a5b-4b6c-8db8-2df2b59c5b2a"
private const val KNOWLEDGE_TYPE = 1
private const val KNOWLEDGE_PARAMETER = 0
private const val KNOWLEDGE_QR_CONTENT = "$KNOWLEDGE_TYPE|$KNOWLEDGE_GUID|$KNOWLEDGE_PARAMETER"

private fun generateQrCode(content: String, sizePx: Int): Bitmap {
    val bitMatrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, sizePx, sizePx)
    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    for (x in 0 until sizePx) {
        for (y in 0 until sizePx) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
        }
    }
    return bitmap
}
