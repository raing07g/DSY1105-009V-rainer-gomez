package com.example.my_app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue // <-- IMPORTANTE: Soluciona el error Property delegate
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.my_app.components.CustomButton
import com.example.my_app.components.ValidatedTextField
import com.example.my_app.viewmodel.RegistroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    onSolicitudGuardada: () -> Unit
) {
    // Escuchamos el StateFlow con 'by'
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Formulario de Solicitud I+D") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Solicitud de Vinculación / Información",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            ValidatedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = "Nombre del Solicitante / Empresa",
                errorMessage = uiState.nombreError
            )

            Spacer(modifier = Modifier.height(8.dp))

            ValidatedTextField(
                value = uiState.correo,
                onValueChange = { viewModel.onCorreoChange(it) },
                label = "Correo Institucional",
                errorMessage = uiState.correoError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Enviar y Guardar en DataStore",
                onClick = {
                    if (viewModel.validarFormulario()) {
                        viewModel.guardarEnDataStore()
                        onSolicitudGuardada()
                    }
                }
            )
        }
    }
}