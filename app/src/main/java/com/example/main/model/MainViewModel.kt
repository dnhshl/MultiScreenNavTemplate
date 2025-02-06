package com.example.main.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.random.Random


// Data class representing the UI state
@Serializable
data class MyUiState(
    // State Variablen
    // Läuft das Spiel?
    val isPlaying: Boolean = false,
    // eingebene Zahl
    val entry: String  = "",
    // zu erratende Zahl
    val numberToGuess: Int = Random.nextInt(1, 101),
    // Aktuelle Ergebnisanzeige
    val result: String = "",
    // Zöhler der Versuche
    val counter: Int = 0,
    // richtig geraten?
    val isCorrect: Boolean = false

)

@Serializable
data class SharedUiState(
    val clickCounter: Int = 0
)





class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val Context.dataStore by preferencesDataStore(name = "ui_state")
    private val dataStore = application.dataStore
    private val UI_STATE_KEY = stringPreferencesKey("ui_state")

    private fun getStringRessource(resId: Int): String {
        return getApplication<Application>().getString(resId)
    }

    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> get() = _uiState


    private val _sharedUiState = MutableStateFlow(SharedUiState())
    val sharedUiState: StateFlow<SharedUiState> get() = _sharedUiState



    init {
        // Load persisted UI state
        viewModelScope.launch {
            getUiState().collect { persistedState ->
                _uiState.value = persistedState
            }
            Log.i(">>>>>", "loading Preferences: ${_uiState.value}")
        }
    }


    // Actions

    fun incrementClickCounter() {
        val currentClickCounter = _sharedUiState.value.clickCounter
        _sharedUiState.value = _sharedUiState.value.copy(clickCounter = currentClickCounter + 1)
    }


    // Snackbar
    // ------------------------------------------------------------------------------
    val snackbarHostState = SnackbarHostState()

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }


    // Funktionen, um den UI-Zustand persistent zu speichern und wiederherzustellen
    // ------------------------------------------------------------------------------

    private fun getUiState(): Flow<MyUiState> =
        dataStore.data.map { preferences ->
            preferences[UI_STATE_KEY]?.let { jsonString ->
                try {
                    Json.decodeFromString(MyUiState.serializer(), jsonString)
                } catch (e: Exception) {
                    MyUiState() // Fallback if parsing fails
                }
            } ?: MyUiState() // Default if no value stored
        }


    private fun saveUiState() {
        val state = _uiState.value
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[UI_STATE_KEY] = Json.encodeToString(MyUiState.serializer(), state)
            }
        }
    }
}
