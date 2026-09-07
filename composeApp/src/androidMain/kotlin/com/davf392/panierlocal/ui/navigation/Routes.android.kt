package com.davf392.panierlocal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

actual class AppNavController(
    val androidNavController: NavHostController
) {
    actual fun navigateTo(route: String) {
        androidNavController.navigate(route)
    }

    actual fun popBackStack() {
        androidNavController.popBackStack()
    }
}

@Composable
actual fun rememberAppNavController() =
    AppNavController(rememberNavController())
