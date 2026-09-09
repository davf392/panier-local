package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable

actual class AppNavController {
    actual fun navigateTo(route: String) {
    }

    actual fun popBackStack() {
    }
}

@Composable
actual fun rememberAppNavController(): AppNavController {
    TODO("Not yet implemented")
}