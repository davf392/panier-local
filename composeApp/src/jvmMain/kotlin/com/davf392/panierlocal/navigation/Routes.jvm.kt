package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

actual class AppNavController(
    val jvmNavController: NavHostController
) {
    actual fun navigateTo(route: String) {
        jvmNavController.navigate(route)
    }

    actual fun popBackStack() {
        jvmNavController.popBackStack()
    }
}

@Composable
actual fun rememberAppNavController() =
    AppNavController(rememberNavController())