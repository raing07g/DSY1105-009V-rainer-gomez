package com.example.my_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.my_app.model.FormStatus
import com.example.my_app.model.RegistroUiState
import com.example.my_app.model.ValidationError
import com.example.my_app.repository.UserPreferencesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

    // Estado reactivo de la pantalla
    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState

    // Funciones para actualizar cada campo a medida que el usuario escribe
    fun onNombreChange(nombre: String) { _uiState.update { it.copy(nombre = nombre) } }
    fun onEmailChange(email: String) { _uiState.update { it.copy(email = email) } }
    fun onPasswordChange(password: String) { _uiState.update { it.copy(password = password) } }
    fun onConfirmPasswordChange(confirm: String) { _uiState.update { it.copy(confirmPassword = confirm) } }
    fun onTerminosChange(aceptados: Boolean) { _uiState.update { it.copy(terminosAceptados = aceptados) } }
    fun onFotoUriChange(uri: String?) { _uiState.update { it.copy(fotoUri = uri) } }

    // Lógica 100% en el ViewModel (desacoplada de la UI)
    fun validarFormulario(): List<ValidationError> {
        val errores = mutableListOf<ValidationError>()
        val state = _uiState.value

        if (state.nombre.isBlank()) errores.add(ValidationError("nombre", "El nombre es obligatorio"))
        if (state.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            errores.add(ValidationError("email", "Ingresa un correo electrónico válido"))
        }
        if (state.password.length < 6) errores.add(ValidationError("password", "La contraseña debe tener al menos 6 caracteres"))
        if (state.password != state.confirmPassword) errores.add(ValidationError("confirmPassword", "Las contraseñas no coinciden"))
        if (!state.terminosAceptados) errores.add(ValidationError("terminos", "Debes aceptar los términos y condiciones"))

        return errores
    }

    fun guardarUsuario() {
        val errores = validarFormulario()
        if (errores.isNotEmpty()) {
            _uiState.update { it.copy(errorMessage = "Corrige los errores antes de continuar") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(status = FormStatus.LOADING, isLoading = true, errorMessage = null) }

            // Retardo simulado de 1.5s para apreciar la animación de carga (CircularProgressIndicator)
            delay(1500)

            repository.saveUserProfile(
                nombre = _uiState.value.nombre,
                email = _uiState.value.email,
                fotoUri = _uiState.value.fotoUri
            )

            _uiState.update { it.copy(status = FormStatus.SUCCESS, isLoading = false) }
        }
    }

    // Factory para poder instanciar el ViewModel con el Repositorio
    companion object {
        fun provideFactory(repository: UserPreferencesRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RegistroViewModel(repository) as T
                }
            }
    }
}