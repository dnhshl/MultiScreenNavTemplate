package com.example.main.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.main.model.MainViewModel
import com.example.main.ui.navigation.MyMenu
import com.example.main.ui.navigation.MyNavBar
import com.example.main.ui.navigation.MyNavHost
import com.example.main.ui.navigation.MyTopBar
import com.example.main.ui.screens.MyScreens.Companion.bottomBarScreens


// Globale Konstante, um das Menü-Icon in der Top-Bar ein- oder auszublenden.
const val SHOW_MENU: Boolean = true

/*
MyApp ist die Haupt-Composable-Funktion, die die gesamte Benutzeroberfläche der App zusammensetzt.
Sie verwendet eine `Scaffold`-Struktur, um die grundlegenden Material-Design-Layout-Elemente
wie TopAppBar, BottomAppBar und den Hauptinhalt zu organisieren.
*/
@Composable
fun MyApp() {

    // rememberNavController() erstellt und merkt sich einen NavController.
    // Dieser ist für die Navigation zwischen den verschiedenen Screens verantwortlich.
    val navController = rememberNavController()

    // viewModel() holt eine Instanz des MainViewModels.
    // Diese Instanz wird an alle Screens weitergegeben, die sie benötigen.
    val viewModel: MainViewModel = viewModel()

    // Beobachtet den Back-Stack der Navigation, um die aktuelle Route zu erhalten.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    // Ein `remember`-Zustand, um die Sichtbarkeit des Menüs zu steuern.
    var showMenu by remember { mutableStateOf(false) }


    // Scaffold ist eine Layout-Struktur, die die Implementierung der grundlegenden
    // Material-Design-UI-Struktur vereinfacht.
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        // Host für die Anzeige von Snackbars.
        snackbarHost = { SnackbarHost(hostState = viewModel.snackbarHostState) },
        // Die obere Anwendungsleiste (TopAppBar).
        topBar = {
            MyTopBar(
                navController = navController,
                onMenuClick = { showMenu = !showMenu }, // Schaltet die Sichtbarkeit des Menüs um.
            )
        },
        // Die untere Navigationsleiste (BottomNavBar).
        bottomBar = {
            // Die Bottom-Bar wird nur auf den Screens angezeigt, die in `bottomBarScreens` definiert sind.
            if (bottomBarScreens.any { it.route == currentRoute }) {
                MyNavBar(
                    navController = navController,
                    screens = MyScreens.bottomBarScreens
                )
            }
        }
    ) { paddingValues ->
        // Der NavHost ist der Bereich, in dem die verschiedenen Screens angezeigt werden.
        // `paddingValues` von der Scaffold wird hier verwendet, um sicherzustellen, dass der Inhalt
        // nicht von der Top- oder Bottom-Bar verdeckt wird.
        MyNavHost(
            navController = navController,
            viewModel = viewModel,
            startDestination = MyScreens.startScreen.route, // Der erste Screen, der angezeigt wird.
            modifier = Modifier.padding(paddingValues)
        )

        // Das seitliche Navigationsmenü.
        MyMenu(
            showMenu = showMenu,
            navController = navController,
            paddingValues = paddingValues,
            onToggleMenu = { showMenu = !showMenu }
       )

    }

}