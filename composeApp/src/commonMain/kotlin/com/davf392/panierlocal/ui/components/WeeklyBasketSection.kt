package com.davf392.panierlocal.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.ui.ArrowRightIcon
import com.davf392.panierlocal.ui.ShoppingCartIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WeeklyBasketSection(
    basket: WeeklyBasketItem? = null,
    isSelected: Boolean = false,
    onSelectBasket: (WeeklyBasketItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }

     Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                basket?.let { onSelectBasket(it) }
            }
            .animateContentSize(animationSpec = tween(300)),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = ShoppingCartIcon,
                        contentDescription = "Panier",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = basket?.name ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Semaine ${basket?.weekNumber} (${basket?.year})",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = basket?.displayTotalPrice ?: "0 €",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = ArrowRightIcon,
                    contentDescription = "Développer le panier",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(if (isSelected) 90f else 0f)
                )
            }
        }
    }
}


@Preview
@Composable
fun WeeklyBasketSectionPreview() {
    PanierLocalTheme {
        WeeklyBasketSection(
            basket = WeeklyBasketItem(
                name = "Panier Tandem Légumes",
                weekNumber = 34,
                year = 2025,
                formula = "Tandem",
                totalPrice = 64.0,
                productsList = emptyList()
            )
        )
    }
}