package com.example.my_app.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.my_app.components.CustomButton
import com.example.my_app.components.ValidatedTextField
import com.example.my_app.model.FormStatus
import com.example.my_app.viewmodel.RegistroViewModel

@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    onNavigateHome: () -> Unit
) {
    // Observamos el estado reactivo del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Selector de imágenes nativo para la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.onFotoUriChange(uri?.toString())
    }

    // Obtenemos los errores dinámicos para pintar los campos de rojo si fallan
    val errores = viewModel.validarFormulario()
    fun getError(campo: String): String? = errores.find { it.campo == campo }?.mensaje

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        ValidatedTextField(
            value = uiState.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = "Nombre completo",
            errorMessage = getError("nombre")
        )

        ValidatedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "Correo electrónico",
            errorMessage = getError("email")
        )

        ValidatedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "Contraseña",
            errorMessage = getError("password")
        )

        ValidatedTextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            label = "Confirmar Contraseña",
            errorMessage = getError("confirmPassword")
        )

        // Checkbox de términos y condiciones
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.terminosAceptados,
                onCheckedChange = { viewModel.onTerminosChange(it) }
            )
            Text("Acepto los términos y condiciones")
        }
        if (getError("terminos") != null) {
            Text(
                text = getError("terminos")!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para invocar la galería (recurso nativo)
        OutlinedButton(onClick = { galleryLauncher.launch("image/*") }) {
            Text(if (uiState.fotoUri == null) "Subir Foto de Perfil" else "Foto Seleccionada ✓")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de error general del formulario
        if (uiState.errorMessage != null) {
            Text(text = uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Botón personalizado (se bloquea si está cargando)
        CustomButton(
            text = "Registrarse",
            onClick = { viewModel.guardarUsuario() },
            enabled = uiState.status != FormStatus.LOADING
        )

        // Animación de estado de carga (CircularProgressIndicator)
        AnimatedVisibility(visible = uiState.status == FormStatus.LOADING) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        // Animación de éxito
        AnimatedVisibility(visible = uiState.status == FormStatus.SUCCESS) {
            Text("¡Registro exitoso guardado en DataStore!", color = MaterialTheme.colorScheme.primary)
        }
    }
}