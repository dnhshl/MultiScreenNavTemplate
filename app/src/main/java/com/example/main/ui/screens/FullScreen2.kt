package com.example.main.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.model.MainViewModel


@Composable
fun FullScreen2(
    viewModel: MainViewModel,
    navController: NavController,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Full Screen 2", fontSize =  24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.showSnackbar(
                message = "Going back to home screen",
                actionLabel = "OK",
                duration = SnackbarDuration.Indefinite
            )
            navController.navigate(MyScreens.Main.route)
        }) {
            Text("go back to home screen")
        }
    }
}