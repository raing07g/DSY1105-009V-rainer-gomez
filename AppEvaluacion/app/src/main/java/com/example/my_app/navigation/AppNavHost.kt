package com.example.my_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.my_app.model.NavigationRoute
import com.example.my_app.screens.HomeScreen
import com.example.my_app.screens.RegistroScreen
import com.example.my_app.screens.PerfilScreen
import com.example.my_app.viewmodel.RegistroViewModel
import com.example.my_app.viewmodel.PerfilViewModel

@Composable
fun AppNavHost(
    registroViewModel: RegistroViewModel,
    perfilViewModel: PerfilViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Home.ruta
    ) {
        composable(NavigationRoute.Home.ruta) {
            HomeScreen(
                onNavigateToRegistro = { navController.navigate(NavigationRoute.Registro.ruta) },
                onNavigateToPerfil = { navController.navigate(NavigationRoute.Perfil.ruta) }
            )
        }
        composable(NavigationRoute.Registro.ruta) {
            RegistroScreen(
                viewModel = registroViewModel,
                onNavigateHome = {
                    navController.navigate(NavigationRoute.Home.ruta) {
                        popUpTo(NavigationRoute.Home.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(NavigationRoute.Perfil.ruta) {
            PerfilScreen(
                viewModel = perfilViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}