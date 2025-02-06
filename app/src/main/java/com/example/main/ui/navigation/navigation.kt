package com.example.main.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.main.R
import com.example.main.model.MainViewModel
import com.example.main.ui.screens.AlertDialogScreen
import com.example.main.ui.screens.FullScreen1
import com.example.main.ui.screens.FullScreen2
import com.example.main.ui.screens.InfoDialogScreen
import com.example.main.ui.screens.MainScreen
import com.example.main.ui.screens.Screen2
import com.example.main.ui.screens.Screen3


// hier "Verwaltungsinfo" zu allen Bildschirmen listen
// ----------------------------------------------------------------

sealed class MyScreens(
    val route: String,
    val titleID: Int = 0,
    val labelID: Int = 0,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val showBackArrow: Boolean = false,
    val content: @Composable (MainViewModel, NavController) -> Unit
) {

    // BottomNavScreens
    // Benötigen Title, Label, Icon, und Lambda Funktion, über die der Screen aufgerufen wird
    // ----------------------------------------------------------------

    object Main : MyScreens(
        route = "main",                          // eindeutige Kennung
        titleID = R.string.mainScreenTitle,      // Titel in der TopBar
        labelID = R.string.mainScreenLabel,      // Label in der BottomBar
        selectedIcon = Icons.Filled.Home,        // Icon in der BottomBar, wenn gewählt
        unselectedIcon =Icons.Outlined.Home,     // Icon in der BottomBar, wenn nicht gewählt
        // Lambda Funktion, über die der Screen aufgerufen wird
        content = { viewModel, navController -> MainScreen(viewModel, navController) }
    )

    object Screen2 : MyScreens(
        route = "screen2",
        titleID = R.string.screen2Title,
        labelID = R.string.screen2Label,
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle,
        content = { viewModel, navController -> Screen2(viewModel, navController) }
    )

    object Screen3 : MyScreens(
        route = "screen3",
        titleID = R.string.screen3Title,
        labelID = R.string.screen3Label,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        content = { viewModel, navController -> Screen3(viewModel, navController) }
    )


    // FullScreens (benötigen keine Icons und kein Label;
    // dafür aber showBackArrow = true für den Zurück Pfeil in der TopBar
    // ----------------------------------------------------------------

    object FullScreen1 : MyScreens(
        route = "fullscreen1",
        titleID = R.string.FullScreen1Title,
        showBackArrow = true,
        content = { viewModel, navController -> FullScreen1(viewModel, navController) }
    )

    object FullScreen2 : MyScreens(
        route = "fullscreen2",
        titleID = R.string.FullScreen2Title,
        showBackArrow = true,
        content = { viewModel, navController -> FullScreen2(viewModel, navController) }
    )

    // Dialog Screens
    // Benötigen nur die route und die content Lambda Funktion
    // ----------------------------------------------------------------

    object InfoDialog : MyScreens(
        route = "info_dialog",
        content = { viewModel, navController -> InfoDialogScreen(viewModel, navController) }
    )

    object AlertDialog : MyScreens(
        route = "alert_dialog",
        content = { viewModel, navController -> AlertDialogScreen(viewModel, navController) }
    )

}


// Hier alle Bildschirme listen, über die in der Bottom Bar navigiert werden soll
// Die Reihenfolge bestimmt die Reihenfolge in der Bottom Bar
val bottomBarScreenList = listOf (
    MyScreens.Main,
    MyScreens.Screen2,
    MyScreens.Screen3,
)


// Hier alle Bildschirme listen, die als FullScreen Bildschirm angesprungen werden sollen
// wenn es keine gibt, dann emptyList()
// val fullScreenList = emptyList<MyScreens>()
val fullScreenList = listOf (
    MyScreens.FullScreen1,
    MyScreens.FullScreen2
)


// Hier alle Bildschirme listen, die als FullScreen Bildschirm angesprungen werden sollen
val screenList = bottomBarScreenList + fullScreenList


// Hier alle Dialogbilschirme listen
// wenn es keine gibt, dann emptyList()
// val dialogScreenList = emptyList<MyScreens>()
val dialogScreenList = listOf (
    MyScreens.InfoDialog,
    MyScreens.AlertDialog
)



