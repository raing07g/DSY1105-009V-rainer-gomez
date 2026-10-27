package com.example.my_app.model

data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val terminosAceptados: Boolean = false,
    val fotoUri: String? = null,
    val status: FormStatus = FormStatus.IDLE,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)