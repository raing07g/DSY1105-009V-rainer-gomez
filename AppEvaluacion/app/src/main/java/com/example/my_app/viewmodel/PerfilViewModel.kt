package com.example.my_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.my_app.model.PerfilUiState
import com.example.my_app.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState

    init {
        cargarPerfil()
    }

    // Leemos los datos de forma reactiva desde DataStore
    private fun cargarPerfil() {
        viewModelScope.launch {
            repository.getUserProfileFlow().collect { profile ->
                _uiState.update {
                    it.copy(
                        nombre = profile.nombre,
                        email = profile.email,
                        fotoUri = profile.fotoUri,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onNombreChange(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre) }
    }

    fun guardarCambios() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.saveUserProfile(
                nombre = _uiState.value.nombre,
                email = _uiState.value.email,
                fotoUri = _uiState.value.fotoUri
            )
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    // Factory para inyectar el repositorio
    companion object {
        fun provideFactory(repository: UserPreferencesRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PerfilViewModel(repository) as T
                }
            }
    }
}