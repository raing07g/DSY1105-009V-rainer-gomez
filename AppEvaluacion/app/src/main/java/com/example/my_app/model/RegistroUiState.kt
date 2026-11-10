package com.example.my_app.model

data class RegistroUIState(
    val nombre: String = "",
    val nombreError: String? = null,
    val correo: String = "",
    val correoError: String? = null
)