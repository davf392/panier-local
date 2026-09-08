package com.davf392.panierlocal.ui.features.exchange

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
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.data.formatWeightDisplay
import com.davf392.panierlocal.data.toLabel
import com.davf392.panierlocal.formatDecimal
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WeightDisplaySection(
    item: ProductItem,
    weightGrams: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Produit rendu :",
            style = MaterialTheme.typography.bodyLarge,
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
            item = ProductItem(
                name = "Carottes",
                pricePerUnit = 2.50,
                unit = ProductUnit.GRAM
            ),
            weightGrams = 500
        )
    }
}
