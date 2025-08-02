package com.davf392.panierlocal

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Panierlocal",
    ) {
        App()
    }
}