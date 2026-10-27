package com.example.my_app.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Creamos la instancia única de DataStore ligada al contexto
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

// Clase auxiliar para empaquetar los datos al leerlos
data class ProfileData(
    val nombre: String,
    val email: String,
    val fotoUri: String?
)

class UserPreferencesRepository(private val context: Context) {

    // Definimos las "llaves" de seguridad para guardar cada dato
    companion object {
        val USER_NOMBRE = stringPreferencesKey("user_nombre")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_FOTO_URI = stringPreferencesKey("user_foto_uri")
    }

    // Función asíncrona (suspend) para guardar los datos en segundo plano
    suspend fun saveUserProfile(nombre: String, email: String, fotoUri: String?) {
        context.dataStore.edit { preferences ->
            preferences[USER_NOMBRE] = nombre
            preferences[USER_EMAIL] = email
            if (fotoUri != null) {
                preferences[USER_FOTO_URI] = fotoUri
            } else {
                preferences.remove(USER_FOTO_URI)
            }
        }
    }

    // Función reactiva (Flow) que avisa automáticamente a la interfaz si el nombre cambia
    fun getNombreFlow(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NOMBRE]
    }

    // Función reactiva (Flow) para leer todo el perfil completo
    fun getUserProfileFlow(): Flow<ProfileData> = context.dataStore.data.map { preferences ->
        ProfileData(
            nombre = preferences[USER_NOMBRE] ?: "",
            email = preferences[USER_EMAIL] ?: "",
            fotoUri = preferences[USER_FOTO_URI]
        )
    }
}