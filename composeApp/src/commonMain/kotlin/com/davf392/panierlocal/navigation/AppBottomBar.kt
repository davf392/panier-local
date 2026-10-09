package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import cafe.adriel.voyager.navigator.Navigator

@Composable
expect fun AppBottomBar(
    navigator: Navigator,
    modifier: Modifier = Modifier
)

