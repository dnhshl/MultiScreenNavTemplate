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


@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    val clickCounter = state.clickCounter

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(clickCounter.toString(), fontSize =  24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.incrementClickCounter()
            //viewModel.showSnackbar("You clicked again!", duration = SnackbarDuration.Indefinite)
        }) {
            Text("Klick mich")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text("Welcome to Home Screen", fontSize =  24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(MyScreens.FullScreen1.route)}) {
            Text("Go to full screen 1")
        }
    }
}