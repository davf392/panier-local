package com.davf392.panierlocal

import androidx.compose.runtime.*
import com.amap.app.ui.screens.BasketScreen
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    PanierLocalTheme {
        BasketScreen()
    }
}