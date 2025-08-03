package com.davf392.panierlocal.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.CommonProduct
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.ui.RefreshIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductItemCard(
    item: CommonProduct,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isSelected: Boolean = false,
    content: @Composable (contentColor: Color) -> Unit,
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.surface
    }
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Partie gauche (emoji et nom du produit)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.emoji,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(end = 12.dp),
                    color = contentColor
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = contentColor
                )
            }

            // Le slot de contenu (le contenu à droite)
            content(contentColor)
        }
    }
}

@Preview
@Composable
fun ProductItemCardPreview() {
    PanierLocalTheme {
        val item = ProductItem(name = "Concombre", emoji = "🥒", quantity = 300.0, unit = "g", pricePerUnit = 3.4, totalPrice = 4.6)
        ProductItemCard(
            item = item,
            content =
                {
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
                        Icon(
                            imageVector = RefreshIcon,
                            contentDescription = "Échanger",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
        )
    }
}