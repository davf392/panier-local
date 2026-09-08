package com.davf392.panierlocal.ui.features.basket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.ui.RefreshIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductBasketSection(
    item: ProductItem,
    onExchangeClicked: (ProductItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = item.displayQuantity,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${item.totalPrice} €",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(
            onClick = { onExchangeClicked(item) },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = RefreshIcon,
                contentDescription = "Échanger",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// --- Previews ---

@Preview
@Composable
fun ProductBasketSectionLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            ProductBasketSection(item = mockProductItem)
        }
    }
}

@Preview
@Composable
fun ProductBasketSectionDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            ProductBasketSection(item = mockProductItem)
        }
    }
}

private val mockProductItem = ProductItem(
    name = "Concombre",
    quantity = 1.0,
    unit = ProductUnit.PIECE,
    pricePerUnit = 1.80,
    totalPrice = 1.80,
)