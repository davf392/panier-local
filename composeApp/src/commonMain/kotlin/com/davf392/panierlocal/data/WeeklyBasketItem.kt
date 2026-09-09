package com.davf392.panierlocal.data

data class WeeklyBasketItem(
    val id: String = "",
    val distributionId: String = "",
    val formula: String = "", // Mini, Solo, Tandem, Famille
    val category: String = "Autres", // Légumes, Fruits, Produits Laitiers
    val expectedCount: Int = 0,
    val actualCount: Int = 0,
    val productsList: List<ProductItem> = emptyList()
)

