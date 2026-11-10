package com.example.my_app.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CircularProfileImage(
    imageUri: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUri.takeIf { !it.isNullOrEmpty() } ?: "https://via.placeholder.com/150",
        contentDescription = "Recurso Nativo del Proyecto",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    )
}