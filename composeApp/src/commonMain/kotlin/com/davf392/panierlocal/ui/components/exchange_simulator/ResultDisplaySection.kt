package com.davf392.panierlocal.ui.components.exchange_simulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.formatDecimal
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ResultDisplaySection(
    itemToExchange: ProductItem = ProductItem(),
    returnedWeightGrams: Int = 0,
    exchangedProduct: ExchangeItem = ExchangeItem(),
    maxWeightGrams: Int = 0,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Vous avez rendu ${formatDecimal(returnedWeightGrams.toDouble(), 0)} g de ${itemToExchange.name}  ${itemToExchange.emoji}",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Vous pouvez prendre jusqu'à\n${formatDecimal(maxWeightGrams.toDouble(),0)} g de ${exchangedProduct.name} ${exchangedProduct.emoji}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF388E3C),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        // Vous pouvez ajouter d'autres informations ou un bouton "Terminer"
    }
}

@Preview
@Composable
fun ResultDisplaySection() {
    PanierLocalTheme {
        ResultDisplaySection(
            itemToExchange = ProductItem(
                id = "1",
                name = "Patate",
                emoji = "🥔",
                quantity = 200.0,
                unit = "g",
                pricePerUnit = 0.7,
                totalPrice = 3.7
            ),
            returnedWeightGrams = 300,
            exchangedProduct = ExchangeItem(name = "Concombre", emoji = "🥒", pricePerKg = 3.45),
            maxWeightGrams = 150,
            modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background
            ),
        )
    }
}