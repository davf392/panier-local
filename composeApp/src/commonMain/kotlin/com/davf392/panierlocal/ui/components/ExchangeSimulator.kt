package com.amap.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ExchangeSimulation
import com.amap.app.data.Product
import com.davf392.panierlocal.ui.RefreshIcon

@Composable
fun ExchangeSimulator(
    exchangeSimulation: ExchangeSimulation,
    availableProducts: List<Product>,
    onProductToReturnSelected: (Product) -> Unit,
    onQuantityChanged: (Double) -> Unit,
    onProductToReceiveSelected: (Product) -> Unit,
    onReset: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Simulateur d'échange",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onReset) {
                    Icon(
                        imageVector = RefreshIcon,
                        contentDescription = "Réinitialiser"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Section 1: Produit à rendre
            Text(
                text = "1. Produit à rendre",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableProducts) { product ->
                    val isSelected = exchangeSimulation.productToReturn?.id == product.id
                    
                    ProductCard(
                        product = product,
                        isSelected = isSelected,
                        onClick = { onProductToReturnSelected(product) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Section 2: Quantité à rendre
            if (exchangeSimulation.productToReturn != null) {
                Text(
                    text = "2. Quantité à rendre",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantité: ",
                        fontSize = 14.sp
                    )
                    OutlinedTextField(
                        value = exchangeSimulation.returnedQuantity.toString(),
                        onValueChange = { value ->
                            val quantity = value.toDoubleOrNull() ?: 0.0
                            onQuantityChanged(quantity)
                        },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        suffix = {
                            Text(
                                text = exchangeSimulation.productToReturn.unit ?: "",
                                fontSize = 12.sp
                            )
                        }
                    )
                    Text(
                        text = " / ${exchangeSimulation.productToReturn.displayQuantity}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Section 3: Produit souhaité
            Text(
                text = "3. Produit souhaité",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableProducts) { product ->
                    val isSelected = exchangeSimulation.productToReceive?.id == product.id
                    
                    ProductCard(
                        product = product,
                        isSelected = isSelected,
                        onClick = { onProductToReceiveSelected(product) }
                    )
                }
            }
            
            // Résultat de l'échange
            if (exchangeSimulation.canExchange) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = RefreshIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = exchangeSimulation.exchangeMessage,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}