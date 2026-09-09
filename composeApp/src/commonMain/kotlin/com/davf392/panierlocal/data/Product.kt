package com.davf392.panierlocal.data

data class Product(
    val id: String = "",
    val name: String = "",
    val unit: ProductUnit? = null,
    val pricePerUnit: Double = 0.0
)
