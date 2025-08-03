package com.davf392.panierlocal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.ui.RefreshIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BasketSection(
    item: ProductItem,
    onExchangeClicked: (ProductItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    ProductItemCard(
        item = item,
        modifier = modifier,
        onClick = {}
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    text = item.displayQuantity,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = item.displayPrice,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { onExchangeClicked(item) }) {
                Icon(
                    imageVector = RefreshIcon,
                    contentDescription = "Échanger",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BasketSectionPreview() {
    PanierLocalTheme {
        val sampleProductItem = ProductItem(
            name = "Concombre",
            emoji = "🥒",
            quantity = 1.0,
            unit = "pièce",
            pricePerUnit = 1.80,
            totalPrice = 1.80,
        )

        BasketSection(
            item = sampleProductItem,
            onExchangeClicked = {},
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        )
    }
}