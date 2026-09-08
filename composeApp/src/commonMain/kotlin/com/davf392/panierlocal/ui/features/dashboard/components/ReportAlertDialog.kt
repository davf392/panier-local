package com.davf392.panierlocal.ui.features.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReportAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var message by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Signaler une alerte") },
        text = {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(message) },
                enabled = message.isNotBlank()
            ) {
                Text("Signaler")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Preview
@Composable
fun ReportAlertDialogPreview() {
    PanierLocalTheme {
        ReportAlertDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}