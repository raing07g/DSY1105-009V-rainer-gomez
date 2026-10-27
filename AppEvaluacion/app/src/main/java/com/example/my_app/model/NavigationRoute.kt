package com.example.my_app.model

sealed class NavigationRoute(val ruta: String) {
    object Home : NavigationRoute("home")
    object Registro : NavigationRoute("registro")
    object Perfil : NavigationRoute("perfil")
    object Configuracion : NavigationRoute("configuracion")
    object Estado : NavigationRoute("estado")
}