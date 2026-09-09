package com.davf392.panierlocal.features.basket

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BasketHistoryButton(
    onViewHistoryClicked: () -> Unit = {}
) {
    Button(
        onClick = onViewHistoryClicked,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = "Historique des anciens paniers")
    }
}

// --- Previews ---

@Preview
@Composable
fun BasketHistoryButtonLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            BasketHistoryButton()
        }
    }
}

@Preview
@Composable
fun BasketHistoryButtonDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            BasketHistoryButton()
        }
    }
}