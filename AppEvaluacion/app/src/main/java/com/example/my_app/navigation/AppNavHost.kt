package com.example.my_app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.my_app.repository.UserPreferencesRepository
import com.example.my_app.screens.DetalleProyectoScreen
import com.example.my_app.screens.HomeScreen
import com.example.my_app.screens.RegistroScreen
import com.example.my_app.viewmodel.RegistroViewModel

@Composable
fun AppNavHost(repository: UserPreferencesRepository) {
    val navController = rememberNavController()

    val registroViewModel: RegistroViewModel = viewModel(
        factory = RegistroViewModel.Factory(repository)
    )

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToDetalle = { proyectoId ->
                    navController.navigate("detalle/$proyectoId")
                },
                onNavigateToRegistro = {
                    navController.navigate("registro")
                }
            )
        }

        composable(
            route = "detalle/{proyectoId}",
            arguments = listOf(
                navArgument("proyectoId") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("proyectoId") ?: "1"
            DetalleProyectoScreen(
                proyectoId = id,
                onNavigateToRegistro = { navController.navigate("registro") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("registro") {
            RegistroScreen(
                viewModel = registroViewModel,
                onSolicitudGuardada = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}