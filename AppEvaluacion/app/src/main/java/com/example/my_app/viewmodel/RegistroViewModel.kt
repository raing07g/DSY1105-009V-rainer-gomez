package com.example.my_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.my_app.model.RegistroUIState
import com.example.my_app.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUIState())
    val uiState: StateFlow<RegistroUIState> = _uiState.asStateFlow()

    fun onNombreChange(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre, nombreError = null) }
    }

    fun onCorreoChange(nuevoCorreo: String) {
        _uiState.update { it.copy(correo = nuevoCorreo, correoError = null) }
    }

    fun validarFormulario(): Boolean {
        var esValido = true
        val current = _uiState.value

        if (current.nombre.isBlank()) {
            _uiState.update { it.copy(nombreError = "El nombre o empresa es obligatorio") }
            esValido = false
        }

        if (current.correo.isBlank() || !current.correo.contains("@")) {
            _uiState.update { it.copy(correoError = "Ingrese un correo válido (ej. usuario@duocuc.cl)") }
            esValido = false
        }

        return esValido
    }

    fun guardarEnDataStore() {
        viewModelScope.launch {
            repository.guardarUsuario(
                nombre = _uiState.value.nombre,
                correo = _uiState.value.correo,
                fotoUri = ""
            )
        }
    }

    // Factory para instanciar el ViewModel pasando el Repository
    class Factory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegistroViewModel(repository) as T
        }
    }
}