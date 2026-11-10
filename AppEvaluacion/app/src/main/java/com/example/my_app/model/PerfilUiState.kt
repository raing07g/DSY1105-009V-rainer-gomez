package com.example.my_app.model

data class PerfilUIState(
    val nombre: String = "",
    val correo: String = "",
    val fotoUri: String = "",
    val isLoading: Boolean = true
)