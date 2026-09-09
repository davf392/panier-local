package com.davf392.panierlocal.features.exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.core.utils.formatDecimal
import com.davf392.panierlocal.core.utils.formatWeightDisplay
import com.davf392.panierlocal.core.utils.toLabel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

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

// --- Previews ---

@Composable
private fun WeightDisplaySectionPreviewContent(weightGrams: Int) {
    WeightDisplaySection(
        item = mockItem,
        weightGrams = weightGrams
    )
}

@Preview
@Composable
private fun WeightDisplaySectionLightPreview(
    @PreviewParameter(WeightDisplayPreviewParameterProvider::class) weightGrams: Int
) {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(modifier = Modifier.padding(16.dp)) {
            WeightDisplaySectionPreviewContent(weightGrams = weightGrams)
        }
    }
}

@Preview
@Composable
private fun WeightDisplaySectionDarkPreview(
    @PreviewParameter(WeightDisplayPreviewParameterProvider::class) weightGrams: Int
) {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.padding(16.dp)) {
            WeightDisplaySectionPreviewContent(weightGrams = weightGrams)
        }
    }
}

private val mockItem = ProductItem(
    name = "Carottes",
    pricePerUnit = 2.50,
    unit = ProductUnit.GRAM
)

class WeightDisplayPreviewParameterProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int> = sequenceOf(500, 1200)
}