package com.davf392.panierlocal.ui.features.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.data.formatWeightDisplay
import com.davf392.panierlocal.data.toLabel
import com.davf392.panierlocal.formatDecimal
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ResultDisplaySection(
    exchangedProduct: ExchangeItem,
    maxWeightGrams: Int,
    onModifySelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Product Reminder
                Text(
                    text = exchangedProduct.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${formatDecimal(exchangedProduct.pricePerUnit, 2)} € / ${exchangedProduct.unit.toLabel(1.0)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (maxWeightGrams > 0) {
                    Text(
                        text = "Vous pouvez prendre :",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatWeightDisplay(maxWeightGrams, exchangedProduct.unit),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = "Valeur insuffisante pour cet article",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onModifySelection,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Modifier ma sélection")
        }
    }
}

@Preview
@Composable
private fun ResultDisplaySectionSuccessPreview() {
    PanierLocalTheme {
        ResultDisplaySection(
            exchangedProduct = ExchangeItem(
                id = "2",
                name = "Pommes de terre",
                unit = ProductUnit.GRAM,
                pricePerUnit = 2.0
            ),
            maxWeightGrams = 1500,
            {}
        )
    }
}

@Preview
@Composable
private fun ResultDisplaySectionInsufficientPreview() {
    PanierLocalTheme {
        ResultDisplaySection(
            exchangedProduct = ExchangeItem(
                id = "2",
                name = "Pommes de terre",
                unit = ProductUnit.GRAM,
                pricePerUnit = 2.0
            ),
            maxWeightGrams = 0,
            {}
        )
    }
}
