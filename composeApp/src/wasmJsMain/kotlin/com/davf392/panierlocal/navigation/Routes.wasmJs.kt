package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class AppNavController {
    actual fun navigateTo(route: String) {
        // Implementation for WasmJs if needed, or empty
    }

    actual fun popBackStack() {
        // Implementation for WasmJs if needed, or empty
    }
}

@Composable
actual fun rememberAppNavController(): AppNavController {
    return remember { AppNavController() }
}
