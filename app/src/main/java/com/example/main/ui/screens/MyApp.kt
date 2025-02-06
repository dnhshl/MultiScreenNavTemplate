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


// Show the menu icon in the top bar
const val SHOW_MENU: Boolean = true

@Composable
fun MyApp() {

    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    var showMenu by remember { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        snackbarHost = { SnackbarHost(hostState = viewModel.snackbarHostState) },
        topBar = {
            MyTopBar(
                navController = navController,
                onMenuClick = { showMenu = !showMenu },
            )
        },
        bottomBar = {
            if (bottomBarScreens.any { it.route == currentRoute }) {
                MyNavBar(
                    navController = navController,
                    screens = MyScreens.bottomBarScreens
                )
            }
        }
    ) { paddingValues ->
        MyNavHost(
            navController = navController,
            viewModel = viewModel,
            startDestination = MyScreens.Main.route,
            modifier = Modifier.padding(paddingValues)
        )

        MyMenu(
            showMenu = showMenu,
            navController = navController,
            paddingValues = paddingValues,
            onToggleMenu = { showMenu = !showMenu }
       )

    }

}