package com.davf392.panierlocal.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen

data class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
)
