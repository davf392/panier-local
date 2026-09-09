package com.davf392.panierlocal.features.exchange

import com.davf392.panierlocal.data.Product
import com.davf392.panierlocal.data.ProductItem

data class ExchangeUiState(
    val itemToExchange: ProductItem,
    val availableProducts: List<Product>,
    val returnedWeightGrams: Int,
    val selectedProduct: Product? = null,
    val exchangeResult: Int,
)