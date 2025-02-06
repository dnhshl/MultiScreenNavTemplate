package com.example.main.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.dialog
import androidx.navigation.compose.composable
import com.example.main.model.MainViewModel
import com.example.main.ui.navigation.MyMenu
import com.example.main.ui.navigation.MyNavBar
import com.example.main.ui.navigation.MyScreens
import com.example.main.ui.navigation.MyTopBar
import com.example.main.ui.navigation.bottomBarScreenList
import com.example.main.ui.navigation.dialogScreenList
import com.example.main.ui.navigation.screenList

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
                screens = screenList,
                onMenuClick = { showMenu = !showMenu },
            )
        },
        bottomBar = {
            if (bottomBarScreenList.any { it.route == currentRoute }) {
                MyNavBar(
                    navController = navController,
                    screens = bottomBarScreenList
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = MyScreens.Main.route,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            // Screens via BottomBar und Fullscreens
            screenList.forEach { screen ->
                composable(screen.route) {
                    screen.content(viewModel, navController)
                }
            }
            // Dialog Screens
            dialogScreenList.forEach { screen ->
                dialog(screen.route) {
                    screen.content(viewModel, navController)
                }
            }
        }


        MyMenu(
            showMenu = showMenu,
            navController = navController,
            paddingValues = paddingValues,
            onToggleMenu = { showMenu = !showMenu }
       )

    }

}