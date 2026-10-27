package com.example.my_app.model

data class PerfilUiState(
    val nombre: String = "",
    val email: String = "",
    val fotoUri: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)