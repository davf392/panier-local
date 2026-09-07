package com.davf392.panierlocal.ui.features.exchange_simulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.data.formatWeightDisplay
import com.davf392.panierlocal.data.toLabel
import com.davf392.panierlocal.formatDecimal

@Composable
fun WeightDisplaySection(
    item: com.davf392.panierlocal.data.ProductItem,
    weightGrams: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = "Produit rendu :",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(
            modifier = modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${formatWeightDisplay(weightGrams, item.unit)} de ${item.name.lowercase()}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${formatDecimal(item.pricePerUnit, 2)} € / ${item.unit.toLabel(1.0)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview
@Composable
fun WeightDisplaySectionPreview() {
    PanierLocalTheme {
        WeightDisplaySection(
            item = com.davf392.panierlocal.data.ProductItem(
                name = "Carottes",
                pricePerUnit = 2.50,
                unit = com.davf392.panierlocal.data.ProductUnit.GRAM
            ),
            weightGrams = 500
        )
    }
}
