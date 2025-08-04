package com.example.main.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
MainScreen ist ein Beispiel für einen Screen in der App.
Jeder Screen ist eine Composable-Funktion, die die UI für diesen bestimmten Bildschirm darstellt.
Er erhält das ViewModel und den NavController als Parameter, um auf die App-Logik und
die Navigation zugreifen zu können.
*/
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController,
) {
    // `collectAsState()` sammelt Werte aus dem StateFlow des ViewModels und wandelt sie in einen
    // Compose-State um. Immer wenn sich der Wert im StateFlow ändert, wird diese Composable
    // automatisch neu komponiert (neu gezeichnet), um die UI zu aktualisieren.
    val state by viewModel.state.collectAsState()
    val clickCounter = state.clickCounter

    // Column ist ein Layout-Composable, das seine Kinder vertikal anordnet.
    Column(
        modifier = Modifier.fillMaxSize(), // Füllt die gesamte verfügbare Größe aus.
        verticalArrangement = Arrangement.Center, // Zentriert die Kinder vertikal.
        horizontalAlignment = Alignment.CenterHorizontally // Zentriert die Kinder horizontal.
    ) {
        // Zeigt den aktuellen Wert des Klickzählers an.
        Text(clickCounter.toString(), fontSize =  24.sp)
        Spacer(modifier = Modifier.height(16.dp)) // Fügt einen leeren Raum hinzu.

        // Ein einfacher Button.
        Button(onClick = {
            // Bei einem Klick wird die entsprechende Funktion im ViewModel aufgerufen.
            // Das ViewModel kümmert sich dann um die Logik (hier: das Erhöhen des Zählers).
            viewModel.incrementClickCounter()
            // Hier könnte man z.B. auch eine Snackbar anzeigen.
            //viewModel.showSnackbar("You clicked again!", duration = SnackbarDuration.Indefinite)
        }) {
            Text("Klick mich")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text("Welcome to Home Screen", fontSize =  24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Button, um zu einem anderen Screen zu navigieren.
        Button(onClick = {
            // `navController.navigate()` wird aufgerufen, um zu der angegebenen Route zu navigieren.
            navController.navigate(MyScreens.FullScreen1.route)
        }) {
            Text("Go to full screen 1")
        }
    }
}