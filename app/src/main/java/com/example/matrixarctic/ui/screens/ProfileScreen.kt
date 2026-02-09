package com.example.matrixarctic.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.example.matrixarctic.R

@Composable
fun ProfileScreen() {
    val name = "Джонни"
    val health = 92

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.profile_image),
            contentDescription = "Profile picture",
            modifier = Modifier.size(120.dp)
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(32.dp))
        Text("Ментальное здоровье", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Text("\uD83E\uDDE0 $health", style = MaterialTheme.typography.displaySmall)
    }
}
