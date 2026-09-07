package com.davf392.panierlocal.data

data class ExchangeItem(
    override val id: String = "",
    override val name: String = "",
    override val emoji: String = "",
    override val unit: ProductUnit? = null,
    override val pricePerUnit: Double = 0.0
) : CommonProduct