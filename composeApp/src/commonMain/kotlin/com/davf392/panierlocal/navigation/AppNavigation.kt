package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    AppNavigationContent(modifier = modifier)
}

@Composable
internal expect fun PlatformAppNavigationContent(
    modifier: Modifier
)
