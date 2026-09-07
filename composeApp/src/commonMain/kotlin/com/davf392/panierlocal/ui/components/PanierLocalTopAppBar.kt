package com.davf392.panierlocal.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davf392.panierlocal.ui.BackIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanierLocalTopAppBar(
    title: String,
    onBackClicked: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (onBackClicked != null) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = BackIcon,
                        contentDescription = "Retour"
                    )
                }
            }
        },
        windowInsets = WindowInsets.statusBars,
        modifier = modifier
    )
}
