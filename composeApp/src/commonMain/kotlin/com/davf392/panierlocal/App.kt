package com.davf392.panierlocal

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.amap.app.ui.screens.BasketScreen
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    PanierLocalTheme {
        BasketScreen(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        )
    }
}