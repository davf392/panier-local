package com.amap.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.amap.app.data.*
import com.davf392.panierlocal.data.Basket
import com.davf392.panierlocal.data.ExchangeSimulation

class BasketViewModel : ViewModel() {
    
    var currentBasket by mutableStateOf<Basket?>(null)
        private set
    
    var exchangeSimulation by mutableStateOf(ExchangeSimulation())
        private set
    
    // Données de test pour le panier
    private val sampleProducts = listOf(
        Product("1", "Salade", "🥗", 1.0, "pièce", 2.50, 2.50),
        Product("2", "Concombre", "🥒", 1.0, "pièce", 1.80, 1.80),
        Product("3", "Oignon blanc", "🧅", 200.0, "g", 0.008, 1.60),
        Product("4", "Tomate cerise", "🍅", 150.0, "g", 0.012, 1.80),
        Product("5", "Aubergine", "🍆", 800.0, "g", 0.004, 3.20)
    )
    
    init {
        loadCurrentBasket()
    }
    
    private fun loadCurrentBasket() {
        currentBasket = Basket(
            id = "basket_2024_15",
            weekNumber = 15,
            year = 2024,
            formula = "Formule Tandem Légumes",
            products = sampleProducts,
            totalPrice = sampleProducts.sumOf { it.totalPrice }
        )
    }
    
    fun selectProductToReturn(product: Product) {
        exchangeSimulation = exchangeSimulation.copy(
            productToReturn = product,
            returnedQuantity = product.quantity
        )
        calculateExchange()
    }
    
    fun updateReturnedQuantity(quantity: Double) {
        exchangeSimulation = exchangeSimulation.copy(
            returnedQuantity = quantity
        )
        calculateExchange()
    }
    
    fun selectProductToReceive(product: Product) {
        exchangeSimulation = exchangeSimulation.copy(
            productToReceive = product
        )
        calculateExchange()
    }
    
    private fun calculateExchange() {
        val productToReturn = exchangeSimulation.productToReturn
        val productToReceive = exchangeSimulation.productToReceive
        val returnedQuantity = exchangeSimulation.returnedQuantity
        
        if (productToReturn != null && productToReceive != null && returnedQuantity > 0) {
            val returnedValue = returnedQuantity * productToReturn.pricePerUnit
            val maxQuantityToReceive = returnedValue / productToReceive.pricePerUnit
            
            exchangeSimulation = exchangeSimulation.copy(
                maxQuantityToReceive = maxQuantityToReceive
            )
        }
    }
    
    fun resetExchange() {
        exchangeSimulation = ExchangeSimulation()
    }
    
    fun getAvailableProductsForExchange(): List<Product> {
        return currentBasket?.products?.filter { product ->
            product.id != exchangeSimulation.productToReturn?.id
        } ?: emptyList()
    }
}