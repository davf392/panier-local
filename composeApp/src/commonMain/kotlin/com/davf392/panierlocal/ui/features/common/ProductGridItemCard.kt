package com.davf392.panierlocal.ui.features.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductGridItemCard(
    item: ExchangeItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isSelected: Boolean = false,
    content: @Composable (contentColor: Color) -> Unit,
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            content(contentColor)
        }
    }
}

@Preview
@Composable
private fun ProductGridItemCardPreview() {
    PanierLocalTheme {
        val item = ExchangeItem(
            name = "Concombre",
            pricePerUnit = 3.4
        )
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ProductGridItemCard(
                item = item,
                content = { contentColor ->
                    Text(
                        text = "${item.pricePerUnit} €/kg",
                        fontSize = 14.sp,
                        color = contentColor
                    )
                }
            )
            ProductGridItemCard(
                item = item,
                isSelected = true,
                content = { contentColor ->
                    Text(
                        text = "${item.pricePerUnit} €/kg",
                        fontSize = 14.sp,
                        color = contentColor
                    )
                }
            )
        }
    }
}
