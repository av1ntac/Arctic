package com.example.matrixarctic.ui.screens

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

internal val profileImageBitmap: ImageBitmap = run {
    val imageBytes = Base64.decode(PROFILE_IMAGE_BASE64, Base64.DEFAULT)
    BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size).asImageBitmap()
}

private val PROFILE_IMAGE_BASE64 = 