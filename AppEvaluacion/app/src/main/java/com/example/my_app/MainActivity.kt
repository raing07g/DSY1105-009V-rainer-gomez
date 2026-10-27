package com.example.my_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.my_app.navigation.AppNavHost
import com.example.my_app.repository.UserPreferencesRepository
import com.example.my_app.viewmodel.RegistroViewModel
import com.example.my_app.viewmodel.PerfilViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = UserPreferencesRepository(applicationContext)

        // Inicializamos los dos ViewModels
        val registroFactory = RegistroViewModel.provideFactory(repository)
        val registroViewModel = ViewModelProvider(this, registroFactory)[RegistroViewModel::class.java]

        val perfilFactory = PerfilViewModel.provideFactory(repository)
        val perfilViewModel = ViewModelProvider(this, perfilFactory)[PerfilViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(
                        registroViewModel = registroViewModel,
                        perfilViewModel = perfilViewModel
                    )
                }
            }
        }
    }
}