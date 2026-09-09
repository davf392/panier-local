package com.davf392.panierlocal.core.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.data.Product
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun ProductItemCard(
    item: ProductItem,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = contentColor
                )
            }
            content(contentColor)
        }
    }
}

// --- Previews ---

@Composable
private fun ProductItemCardPreviewContent(isSelected: Boolean) {
    val previewProductItem = ProductItem(
        product = Product(
            id = "1",
            name = "Concombre",
            unit = ProductUnit.GRAM,
            pricePerUnit = 3.4
        ),
        quantity = 300.0
    )
    ProductItemCard(
        item = previewProductItem,
        isSelected = isSelected
    ) { contentColor ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = previewProductItem.displayQuantity,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
                Text(
                    text = previewProductItem.displayPrice,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor.copy(alpha = 0.7f)
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = RefreshIcon,
                    contentDescription = "Échanger",
                    tint = contentColor.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductItemCardLightPreview(
    @PreviewParameter(ProductItemPreviewParameterProvider::class) isSelected: Boolean
) {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(modifier = Modifier.padding(16.dp)) {
            ProductItemCardPreviewContent(isSelected = isSelected)
        }
    }
}

@Preview
@Composable
private fun ProductItemCardDarkPreview(
    @PreviewParameter(ProductItemPreviewParameterProvider::class) isSelected: Boolean
) {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.padding(16.dp)) {
            ProductItemCardPreviewContent(isSelected = isSelected)
        }
    }
}

class ProductItemPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(false, true)
}