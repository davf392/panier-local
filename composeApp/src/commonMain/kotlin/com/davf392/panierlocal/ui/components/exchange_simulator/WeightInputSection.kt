package com.davf392.panierlocal.ui.components.exchange_simulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WeightInputSection(
    defaultWeightGrams: Int = 0,
    modifier: Modifier = Modifier
) {
    var weightText by remember { mutableStateOf(defaultWeightGrams.toString()) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Saisissez le poids que vous rendez :",
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = weightText,
            onValueChange = {
                weightText = it
                isError = it.toIntOrNull() == null || it.toIntOrNull()!! <= 0
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Poids rendu (en grammes)") },
            singleLine = true,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (isError) {
            Text(text = "Veuillez entrer un poids valide.", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview
@Composable
fun WeightInputSection() {
    PanierLocalTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            WeightInputSection()
        }
    }
}