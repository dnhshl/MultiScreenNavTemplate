package com.example.main.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.model.MainViewModel

/*
Screen3 demonstriert die Interaktion mit dem persistenten Zustand (pState).
Änderungen, die hier gemacht werden (z.B. die Eingabe eines Namens), bleiben
auch nach dem Neustart der App erhalten, da sie im DataStore gespeichert werden.
*/
@Composable
fun Screen3(viewModel: MainViewModel, navController: NavController) {

    // Hier wird der persistente Zustand (pState) vom ViewModel abonniert.
    val pstate by viewModel.pState.collectAsState()
    val name = pstate.name


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hallo Screen 3", fontSize =  24.sp)
        Spacer(modifier = Modifier.size(16.dp))

        // TextField ist ein Eingabefeld.
        TextField(
            value = name, // Der aktuelle Wert des Feldes wird aus dem pState geholt.
            label = { Text("Name") }, // Die Beschriftung des Feldes.
            // onValueChange wird bei jeder Eingabe aufgerufen.
            onValueChange = {
                // Die onNameChange-Funktion im ViewModel wird aufgerufen, um den Zustand zu aktualisieren.
                viewModel.onNameChange(it)
            },
            singleLine = true // Sorgt dafür, dass das Eingabefeld nur eine Zeile hat.
        )

    }
}