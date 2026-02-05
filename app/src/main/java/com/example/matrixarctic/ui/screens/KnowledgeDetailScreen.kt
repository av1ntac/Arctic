package com.example.matrixarctic.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun KnowledgeDetailScreen() {
    val qrSizePx = with(LocalDensity.current) { 240.dp.roundToPx() }
    var qrImage by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Код замка", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Text("Код замка №424. Получен и проверен.")
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
            onClick = {
                qrImage = generateQrCode(KNOWLEDGE_GUID, qrSizePx).asImageBitmap()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поделиться ")
        }
    }
}

private const val KNOWLEDGE_GUID = "2f05c48c-3a5b-4b6c-8db8-2df2b59c5b2a"

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
