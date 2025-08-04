package com.example.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.main.model.MainViewModel
import com.example.main.ui.screens.AlertDialogScreen
import com.example.main.ui.screens.FullScreen1
import com.example.main.ui.screens.FullScreen2
import com.example.main.ui.screens.InfoDialogScreen
import com.example.main.ui.screens.MainScreen
import com.example.main.ui.screens.MyScreens
import com.example.main.ui.screens.Screen2
import com.example.main.ui.screens.Screen3

/*
MyNavHost ist verantwortlich für die Definition des Navigationsgraphen der App.
Er verwendet die `NavHost`-Composable von Jetpack Navigation, um zu deklarieren,
welcher Screen (Composable) für welche Route angezeigt werden soll.
*/
@Composable
fun MyNavHost(
    navController: NavHostController, // Der NavController, der die Navigation steuert.
    viewModel: MainViewModel,         // Das ViewModel, das an die Screens weitergegeben wird.
    startDestination: String,         // Die Route des Start-Screens.
    modifier: Modifier = Modifier
) {
    // NavHost ist der Container, der die Screens basierend auf der aktuellen Route anzeigt.
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        /*
        Hier werden die einzelnen Screens und Dialoge des Navigationsgraphen definiert.
        `composable(route) { ... }` verknüpft eine Route (einen String) mit einer Composable-Funktion.
        Wenn der NavController zu einer bestimmten Route navigiert, wird die zugehörige Composable
        aufgerufen und angezeigt.
        */

        // Definition der Haupt-Screens, die über die Bottom-Bar oder andere Aktionen erreicht werden.
        composable(MyScreens.Main.route) { MainScreen(viewModel, navController) }
        composable(MyScreens.Screen2.route) { Screen2(viewModel, navController) }
        composable(MyScreens.Screen3.route) { Screen3(viewModel, navController) }
        composable(MyScreens.FullScreen1.route) { FullScreen1(viewModel, navController) }
        composable(MyScreens.FullScreen2.route) { FullScreen2(viewModel, navController) }

        // Definition von Dialog-Screens.
        // `dialog(route) { ... }` ist eine spezielle Art von Ziel, die den Inhalt als Dialog anzeigt.
        dialog(MyScreens.AlertDialog.route) { AlertDialogScreen(viewModel, navController) }
        dialog(MyScreens.InfoDialog.route) { InfoDialogScreen(viewModel, navController) }
    }
}
