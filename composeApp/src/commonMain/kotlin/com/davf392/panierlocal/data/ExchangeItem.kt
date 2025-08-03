package com.davf392.panierlocal.data

data class ExchangeItem(
    override val name: String = "",
    override val emoji: String = "",
    val pricePerKg: Double = 0.0
) : CommonProduct