package com.davf392.panierlocal.features.staff_dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import org.jetbrains.compose.resources.stringResource
import panierlocal.composeapp.generated.resources.Res
import panierlocal.composeapp.generated.resources.cancel
import panierlocal.composeapp.generated.resources.description
import panierlocal.composeapp.generated.resources.report
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReportAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = null,
        text = {
            ReportAlertDialogContent(
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    )
}

@Composable
fun ReportAlertDialogContent(
    onDismiss: () -> Unit = {},
    onConfirm: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var message by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Signaler une alerte",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text(stringResource(Res.string.description)) },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(Res.string.cancel))
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = { onConfirm(message) },
                    enabled = message.isNotBlank()
                ) {
                    Text(stringResource(Res.string.report))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReportAlertDialogLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(modifier = Modifier.padding(16.dp)) {
            ReportAlertDialogContent()
        }
    }
}

@Preview
@Composable
private fun ReportAlertDialogDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.padding(16.dp)) {
            ReportAlertDialogContent()
        }
    }
}