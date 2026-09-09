package com.davf392.panierlocal.repository

import com.davf392.panierlocal.data.Product
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.WeeklyBasketItem

interface IProductRepository {
    suspend fun getWeeklyBasketList(): List<WeeklyBasketItem>
    suspend fun getProductById(id: String): ProductItem?
    suspend fun getAvailableProductsForExchange(selectedItem: ProductItem): List<Product>
    }