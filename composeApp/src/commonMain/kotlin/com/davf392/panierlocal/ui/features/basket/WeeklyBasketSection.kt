package com.davf392.panierlocal.ui.features.basket

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.ui.AddIcon
import com.davf392.panierlocal.ui.ArrowRightIcon
import com.davf392.panierlocal.ui.RemoveIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WeeklyBasketSection(
    basket: WeeklyBasketItem? = null,
    isSelected: Boolean = false,
    onSelectBasket: (WeeklyBasketItem) -> Unit = {},
    onUpdateCount: (String, Int) -> Unit = { _, _ -> },
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
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = basket?.formula ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Attendus: ${basket?.expectedCount ?: 0}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { basket?.let { onUpdateCount(it.id, it.actualCount - 1) } }) {
                    Icon(RemoveIcon, contentDescription = "Diminuer", tint = MaterialTheme.colorScheme.onSecondary)
                }
                Text("${basket?.actualCount ?: 0}", color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight.Bold)
                IconButton(onClick = { basket?.let { onUpdateCount(it.id, it.actualCount + 1) } }) {
                    Icon(AddIcon, contentDescription = "Augmenter", tint = MaterialTheme.colorScheme.onSecondary)
                }
                Spacer(modifier = Modifier.width(8.dp))
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
                id = "1",
                formula = "Tandem",
                expectedCount = 20,
                actualCount = 15,
                productsList = emptyList()
            )
        )
    }
}
