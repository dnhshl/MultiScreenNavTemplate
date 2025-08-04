package com.example.main.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Definiert den DataStore für die App. `ui_state` ist der Name der Datei, in der die Daten gespeichert werden.
private val Context.dataStore by preferencesDataStore(name = "ui_state")

/*
Das MainViewModel ist das Herzstück der App-Logik. Es überlebt Konfigurationsänderungen
(wie z.B. das Drehen des Bildschirms) und hält die Daten für die UI.
Es erbt von AndroidViewModel, um Zugriff auf den Application Context zu haben,
was für den DataStore benötigt wird.
*/
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // SnackbarHostState wird verwendet, um Snackbars (kurze Nachrichten am unteren Bildschirmrand) anzuzeigen.
    val snackbarHostState = SnackbarHostState()
    // Instanz des DataStores, um auf die gespeicherten Daten zuzugreifen.
    private val dataStore = application.dataStore
    // Unser DatastoreManager, der die Lese- und Schreibvorgänge für den DataStore kapselt.
    private val datastoreManager = DatastoreManager(dataStore)

    /*
    Zustandsverwaltung (State Management)
    -----------------------------------
    Wir verwenden StateFlow, um den Zustand der UI zu halten.
    StateFlow ist ein spezieller Flow, der den aktuellen Zustand speichert und an die UI sendet.
    Die UI "abonniert" diese Flows und wird automatisch aktualisiert, wenn sich der Zustand ändert.

    Es gibt zwei Arten von Zustand:
    1. Persistenter Zustand: Daten, die auch nach dem Schließen der App erhalten bleiben sollen (z.B. Benutzereinstellungen).
    2. Nicht-persistenter (flüchtiger) Zustand: Daten, die nur während der Laufzeit der App benötigt werden (z.B. Ladezustände, Zähler).
    */

    // Persistenter State
    // _pState ist privat und veränderlich (MutableStateFlow), damit nur das ViewModel den Zustand ändern kann.
    private val _pState = MutableStateFlow(PersistantUiState())
    // pState ist öffentlich und nur lesbar (StateFlow), damit die UI den Zustand beobachten, aber nicht direkt ändern kann.
    val pState: StateFlow<PersistantUiState> get() = _pState

    // non persistenter State
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state


    /*
    Der init-Block wird ausgeführt, wenn das ViewModel zum ersten Mal erstellt wird.
    Hier werden "Beobachter" (Coroutinen) gestartet, die auf Zustandsänderungen lauschen.
    */
    init {
        // Lade den persistenten UI-Zustand aus dem DataStore, wenn das ViewModel startet.
        viewModelScope.launch {
            datastoreManager.getPersistantState().collectLatest { persistedState ->
                _pState.value = persistedState
            }
            Log.i(">>>>>", "loading Preferences: ${_pState.value}")
        }


        // Überwache den persistant state und speichere ihn bei jeder Änderung im DataStore.
        // `collectLatest` sorgt dafür, dass bei einer schnellen Folge von Änderungen nur die letzte gespeichert wird.
        viewModelScope.launch {
            _pState.collectLatest {
                val pState = _pState.value
                datastoreManager.savePersitantState(pState)
            }
        }

        // Überwache den nicht-persistenten state und führe Aktionen aus, wenn bestimmte Bedingungen erfüllt sind.
        // Hier als Beispiel: Wenn der Zähler durch 5 teilbar ist, zeige eine Snackbar.
        viewModelScope.launch {
            _state.collectLatest {
                val counter = _state.value.clickCounter
                if (counter % 5 == 0 && counter > 0) {
                    showSnackbar("Counter ist durch 5 teilbar")
                }
            }
        }
    }


    /*
    Actions (Aktionen)
    -------------------
    Dies sind die Funktionen, die von der UI aufgerufen werden, um den Zustand der App zu ändern.
    Sie kapseln die Logik zur Zustandsänderung.
    */

    // Erhöht den Klickzähler im nicht-persistenten Zustand.
    fun incrementClickCounter() {
        val currentClickCounter = _state.value.clickCounter
        // `update` ist eine sichere Methode, um den StateFlow-Wert zu aktualisieren.
        _state.update { it.copy(clickCounter = currentClickCounter + 1) }
    }

    // Ändert den Namen im persistenten Zustand.
    fun onNameChange(name: String) {
        _pState.update { it.copy(name = name) }
    }



    // Ab hier Helper Funktionen
    // ------------------------------------------------------------------------------


    // Snackbar
    // ------------------------------------------------------------------------------

    fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        Log.i(">>>>>", "showSnackbar: $message")
        viewModelScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration
            )
        }
    }



    // Zugriff auf String Ressourcen
    // ------------------------------------------------------------------------------
    private fun getStringRessource(resId: Int): String {
        return getApplication<Application>().getString(resId)
    }
}
