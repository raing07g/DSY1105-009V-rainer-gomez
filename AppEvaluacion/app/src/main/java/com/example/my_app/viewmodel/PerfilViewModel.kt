package com.example.my_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.my_app.model.PerfilUIState
import com.example.my_app.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUIState())
    val uiState: StateFlow<PerfilUIState> = _uiState.asStateFlow()

    init {
        cargarPerfil()
    }

    private fun cargarPerfil() {
        viewModelScope.launch {
            repository.userData.collect { data ->
                _uiState.update {
                    it.copy(
                        nombre = data.nombre,
                        correo = data.correo,
                        fotoUri = data.fotoUri,
                        isLoading = false
                    )
                }
            }
        }
    }

    // Factory para inicializar en MainActivity o AppNavHost si se requiere
    class Factory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PerfilViewModel(repository) as T
        }
    }
}