package com.example.my_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.my_app.components.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProyectoScreen(
    proyectoId: String,
    onNavigateToRegistro: () -> Unit,
    onBack: () -> Unit
) {
    val tituloProyecto = when (proyectoId) {
        "1" -> "Torre Peñuelas I+D"
        "2" -> "Ensayos Estructurales"
        "3" -> "Placas de Retención Solar"
        else -> "Proyecto I+D CIM UC"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ficha Técnica I+D") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder nativo y seguro sin Coil (Evita cierres/crashes de red)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CIM UC",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = tituloProyecto,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Documentos y Archivos Asociados:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "📄 Ficha_Tecnica_Proyecto_$proyectoId.pdf")
                    Text(text = "📄 Manual_Ensayos_Madera_CIM.pdf")
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Investigador / Equipo a cargo:",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(text = "Dra. María González - Centro UC Innovación en Madera")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                text = "Expresar Interés / Solicitar Archivos",
                onClick = onNavigateToRegistro
            )
        }
    }
}