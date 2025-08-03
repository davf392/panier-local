package com.davf392.panierlocal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.formatDecimal
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ResultDisplaySection(
    itemToExchange: ExchangeItem,
    returnedWeightGrams: Int,
    exchangedProduct: ExchangeItem,
    maxWeightGrams: Int
) {
    val maxWeightKg = maxWeightGrams / 1000.0
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Vous avez rendu ${formatDecimal(returnedWeightGrams.toDouble(), 0)} de ${itemToExchange.name}  ${itemToExchange.emoji}",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Vous pouvez prendre jusqu'à ${formatDecimal(maxWeightGrams.toDouble(),0)} de ${exchangedProduct.name} ${exchangedProduct.emoji}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF388E3C),
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        // Vous pouvez ajouter d'autres informations ou un bouton "Terminer"
    }
}

@Preview
@Composable
fun ResultDisplaySection() {
    PanierLocalTheme {
        ResultDisplaySection()
    }
}