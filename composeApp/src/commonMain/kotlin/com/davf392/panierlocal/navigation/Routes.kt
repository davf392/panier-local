package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable

object Routes {
    const val DASHBOARD = "dashboard"
    const val WEEKLY_BASKET = "weekly_basket"
    const val MEMBERS = "members"
    const val MEMBER_DETAILS = "member_details"
    const val EXCHANGE_SIMULATOR = "exchange_simulator"
    const val BASKET_HISTORY = "basket_history"
}

expect class AppNavController {
    fun navigateTo(route: String)
    fun popBackStack()
}

@Composable
expect fun rememberAppNavController(): AppNavController