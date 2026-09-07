package com.davf392.panierlocal.data.staff_dashboard

data class BasketFormulaSummary(
    val id: String,
    val name: String, // e.g., "Mini", "Solo", "Tandem", "Famille"
    val expectedCount: Int,
    val actualCount: Int
)
