package com.davf392.panierlocal.data

enum class ProductUnit(val label: String) {
    PIECE("pièce"),
    GRAM("grammes"),
    KILOGRAM("kilogrammes");

    companion object Companion {
        fun fromString(unitString: String): ProductUnit? {
            return entries.find { it.label.equals(unitString, ignoreCase = true) }
        }
    }
}