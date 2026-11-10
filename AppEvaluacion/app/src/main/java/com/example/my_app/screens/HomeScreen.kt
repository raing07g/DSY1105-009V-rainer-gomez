package com.example.my_app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.my_app.components.CustomButton

data class ProyectoCIM(
    val id: String,
    val titulo: String,
    val area: String,
    val estado: String
)

val listaProyectosCIM = listOf(
    ProyectoCIM("1", "Torre Peñuelas I+D", "Sistemas Constructivos", "En Desarrollo"),
    ProyectoCIM("2", "Ensayos Estructurales", "Laboratorio y Certificación", "Disponible"),
    ProyectoCIM("3", "Placas de Retención Solar", "Sustentabilidad y Madera", "Finalizado")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetalle: (String) -> Unit,
    onNavigateToRegistro: () -> Unit
) {
    var filtro by remember { mutableStateOf("") }

    val proyectosFiltrados = remember(filtro) {
        listaProyectosCIM.filter {
            it.titulo.contains(filtro, ignoreCase = true) || it.area.contains(filtro, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("CIM UC - Catálogo I+D") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Exploración de Capacidades e Innovación",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = filtro,
                onValueChange = { filtro = it },
                label = { Text("Buscar proyecto o área I+D...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = proyectosFiltrados,
                    key = { it.id } // Previene recomposiciones pesadas y bloqueos
                ) { proyecto ->
                    Card(
                        onClick = { onNavigateToDetalle(proyecto.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = proyecto.titulo, style = MaterialTheme.typography.titleSmall)
                            Text(text = "Área: ${proyecto.area}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Estado: ${proyecto.estado}", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            CustomButton(
                text = "Solicitar Colaboración / Contacto",
                onClick = onNavigateToRegistro
            )
        }
    }
}