package com.example.main.ui.screens


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.main.R


// hier "Verwaltungsinfo" zu allen Bildschirmen listen
// ----------------------------------------------------------------

sealed class MyScreens(
    val route: String,
    val titleID: Int = R.string.emptyString,
    val labelID: Int = R.string.emptyString,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val showBackArrow: Boolean = false
) {

    // BottomNavScreens benötigen Title, Label, Icons
    // ----------------------------------------------------------------

    object Main : MyScreens(
        route = "main",                          // eindeutige Kennung
        titleID = R.string.mainScreenTitle,      // Titel in der TopBar
        labelID = R.string.mainScreenLabel,      // Label in der BottomBar
        selectedIcon = Icons.Filled.Home,        // Icon in der BottomBar, wenn gewählt
        unselectedIcon =Icons.Outlined.Home,     // Icon in der BottomBar, wenn nicht gewählt
    )

    object Screen2 : MyScreens(
        route = "screen2",
        titleID = R.string.screen2Title,
        labelID = R.string.screen2Label,
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle,
    )

    object Screen3 : MyScreens(
        route = "screen3",
        titleID = R.string.screen3Title,
        labelID = R.string.screen3Label,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
    )


    // FullScreens benötigen keine Icons und kein Label;
    // dafür aber showBackArrow = true für den Zurück Pfeil in der TopBar
    // ----------------------------------------------------------------

    object FullScreen1 : MyScreens(
        route = "fullscreen1",
        titleID = R.string.FullScreen1Title,
        showBackArrow = true,
    )

    object FullScreen2 : MyScreens(
        route = "fullscreen2",
        titleID = R.string.FullScreen2Title,
        showBackArrow = true,
    )

    // Dialog Screens benötigen nur die route
    // ----------------------------------------------------------------

    object InfoDialog : MyScreens(
        route = "info_dialog",
    )

    object AlertDialog : MyScreens(
        route = "alert_dialog",
    )




    companion object {
        val allScreens =
            listOf(Main, Screen2, Screen3, FullScreen1, FullScreen2, AlertDialog, InfoDialog)

        val bottomBarScreens = listOf(Main, Screen2, Screen3)

        fun fromRoute(route: String): MyScreens? =
            allScreens.firstOrNull { it.route == route }
    }

}



