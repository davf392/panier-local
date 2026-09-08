package com.davf392.panierlocal.repository

import androidx.compose.runtime.mutableStateListOf
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.data.WeeklyBasketItem

class ProductRepository: IProductRepository {

    override suspend fun getWeeklyBasketList(): List<WeeklyBasketItem> {
        return mutableStateListOf(
            WeeklyBasketItem(
                id = "TANDEM_LEGUMES_001",
                name = "Tandem Légumes",
                weekNumber = 15,
                year = 2024,
                totalPrice = 10.90,
                formula = "Tandem",
                productsList = listOf(
                    ProductItem(id = "1", name = "Salade", quantity = 1.0, unit = ProductUnit.PIECE, pricePerUnit = 2.50, totalPrice = 2.50),
                    ProductItem(id = "2", name = "Concombre", quantity = 1.0, unit = ProductUnit.PIECE, pricePerUnit = 1.80, totalPrice = 1.80),
                    ProductItem(id = "3", name = "Oignon blanc", quantity = 200.0, unit = ProductUnit.GRAM, pricePerUnit = 1.60, totalPrice = 1.60),
                    ProductItem(id = "4", name = "Tomate", quantity = 150.0, unit = ProductUnit.GRAM, pricePerUnit = 1.80, totalPrice = 1.80),
                    ProductItem(id = "5", name = "Aubergine", quantity = 800.0, unit = ProductUnit.GRAM, pricePerUnit = 3.20, totalPrice = 3.20)
                )
            ),
            WeeklyBasketItem(
                id = "SOLO_FRUITS_001",
                name = "Solo Fruits",
                weekNumber = 15,
                year = 2024,
                totalPrice = 8.50,
                formula = "Solo",
                productsList = listOf(
                    ProductItem(id = "6", name = "Banane", quantity = 500.0, unit = ProductUnit.GRAM, pricePerUnit = 3.0, totalPrice = 1.50),
                    ProductItem(id = "7", name = "Pomme", quantity = 1.0, unit = ProductUnit.KILOGRAM, pricePerUnit = 3.50, totalPrice = 3.50),
                    ProductItem(id = "8", name = "Poire", quantity = 500.0, unit = ProductUnit.GRAM, pricePerUnit = 7.0, totalPrice = 3.50)
                )
            )
        )
    }

    override suspend fun getAvailableProductsForExchange(selectedItem: ProductItem): List<ExchangeItem> {
        return listOf(
            ExchangeItem(id = "1", name = "Salade", unit = ProductUnit.PIECE, pricePerUnit = 2.50),
            ExchangeItem(id = "2", name = "Concombre", unit = ProductUnit.PIECE, pricePerUnit = 1.80),
            ExchangeItem(id = "3", name = "Oignon blanc", unit = ProductUnit.GRAM, pricePerUnit = 1.60),
            ExchangeItem(id = "4", name = "Tomate", unit = ProductUnit.GRAM, pricePerUnit = 1.80),
            ExchangeItem(id = "5", name = "Aubergine", unit = ProductUnit.GRAM, pricePerUnit = 3.20),
            ExchangeItem(id = "6", name = "Banane", unit = ProductUnit.GRAM, pricePerUnit = 3.0),
            ExchangeItem(id = "7", name = "Pomme", unit = ProductUnit.KILOGRAM, pricePerUnit = 3.50),
            ExchangeItem(id = "8", name = "Poire", unit = ProductUnit.GRAM, pricePerUnit = 7.0),
        )
            .filter { item -> item.name != selectedItem.name }
    }
}