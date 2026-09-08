package com.davf392.panierlocal.data

interface CommonProduct {
    val id: String
    val name: String
    val unit: ProductUnit?
    val pricePerUnit: Double
}
