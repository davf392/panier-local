package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
internal actual fun PlatformAppNavigationContent(
    modifier: Modifier
) {
    Navigator(screen = DashboardScreenVoyager) { navigator ->
        SlideTransition(navigator)
    }
}
