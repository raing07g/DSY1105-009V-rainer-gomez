package com.example.my_app.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

data class UserData(
    val nombre: String,
    val correo: String,
    val fotoUri: String
)

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val NOMBRE_KEY = stringPreferencesKey("nombre")
        val CORREO_KEY = stringPreferencesKey("correo")
        val FOTO_URI_KEY = stringPreferencesKey("foto_uri")
    }

    // Función suspendida para guardar los datos en DataStore
    suspend fun guardarUsuario(nombre: String, correo: String, fotoUri: String) {
        context.dataStore.edit { preferences ->
            preferences[NOMBRE_KEY] = nombre
            preferences[CORREO_KEY] = correo
            preferences[FOTO_URI_KEY] = fotoUri
        }
    }

    // Flow reactivo para consultar los datos en tiempo real
    val userData: Flow<UserData> = context.dataStore.data.map { preferences ->
        UserData(
            nombre = preferences[NOMBRE_KEY] ?: "",
            correo = preferences[CORREO_KEY] ?: "",
            fotoUri = preferences[FOTO_URI_KEY] ?: ""
        )
    }
}