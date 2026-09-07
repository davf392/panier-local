package com.davf392.panierlocal.usecase

import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateExchangeUseCaseTest {

    private val useCase = CalculateExchangeUseCase()

    @Test
    fun `test exchange same gram unit`() {
        val itemToExchange = ProductItem(name = "Oignon", unit = ProductUnit.GRAM, pricePerUnit = 1.0, quantity = 500.0)
        val exchangedAgainst = ExchangeItem(name = "Tomate", unit = ProductUnit.GRAM, pricePerUnit = 1.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 500.0)
        assertEquals(500, result)
    }

    @Test
    fun `test exchange gram to piece`() {
        val itemToExchange = ProductItem(name = "Oignon", unit = ProductUnit.GRAM, pricePerUnit = 2.0, quantity = 500.0)
        val exchangedAgainst = ExchangeItem(name = "Salade", unit = ProductUnit.PIECE, pricePerUnit = 0.5)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 500.0)
        assertEquals(2, result)
    }

    @Test
    fun `test exchange piece to kilogram`() {
        val itemToExchange = ProductItem(name = "Concombre", unit = ProductUnit.PIECE, pricePerUnit = 2.0, quantity = 1.0)
        val exchangedAgainst = ExchangeItem(name = "Pomme", unit = ProductUnit.KILOGRAM, pricePerUnit = 4.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 1.0)
        assertEquals(500, result)
    }

    @Test
    fun `test insufficient value`() {
        val itemToExchange = ProductItem(name = "Oignon", unit = ProductUnit.GRAM, pricePerUnit = 1.0, quantity = 100.0)
        val exchangedAgainst = ExchangeItem(name = "Salade", unit = ProductUnit.PIECE, pricePerUnit = 2.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 100.0)
        assertEquals(0, result)
    }

    @Test
    fun `test exchange exact value`() {
        val itemToExchange = ProductItem(name = "Produit", unit = ProductUnit.GRAM, pricePerUnit = 2.0, quantity = 500.0)
        val exchangedAgainst = ExchangeItem(name = "Autre", unit = ProductUnit.KILOGRAM, pricePerUnit = 2.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 500.0)
        assertEquals(500, result)
    }

    @Test
    fun `test zero price handling`() {
        val itemToExchange = ProductItem(name = "Gratuit", unit = ProductUnit.GRAM, pricePerUnit = 0.0, quantity = 100.0)
        val exchangedAgainst = ExchangeItem(name = "Pomme", unit = ProductUnit.KILOGRAM, pricePerUnit = 2.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 100.0)
        assertEquals(0, result)
    }

    @Test
    fun `test very large quantity`() {
        val itemToExchange = ProductItem(name = "Blé", unit = ProductUnit.GRAM, pricePerUnit = 0.001, quantity = 1000000.0)
        val exchangedAgainst = ExchangeItem(name = "Pomme", unit = ProductUnit.GRAM, pricePerUnit = 1.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 1000000.0)
        assertEquals(1000, result)
    }

    @Test
    fun `test exchange kilogram to piece`() {
        val itemToExchange = ProductItem(name = "Pomme", unit = ProductUnit.KILOGRAM, pricePerUnit = 2.0, quantity = 1.0)
        val exchangedAgainst = ExchangeItem(name = "Salade", unit = ProductUnit.PIECE, pricePerUnit = 1.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 1.0)
        assertEquals(2, result)
    }

    @Test
    fun `test exchange gram to kilogram`() {
        val itemToExchange = ProductItem(name = "Oignon", unit = ProductUnit.GRAM, pricePerUnit = 1.0, quantity = 500.0)
        val exchangedAgainst = ExchangeItem(name = "Pomme", unit = ProductUnit.KILOGRAM, pricePerUnit = 2.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 500.0)
        assertEquals(250, result)
    }

    @Test
    fun `test exchange piece to gram`() {
        val itemToExchange = ProductItem(name = "Concombre", unit = ProductUnit.PIECE, pricePerUnit = 2.0, quantity = 1.0)
        val exchangedAgainst = ExchangeItem(name = "Oignon", unit = ProductUnit.GRAM, pricePerUnit = 4.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 1.0)
        assertEquals(500, result)
    }

    @Test
    fun `test exchange kilogram to gram`() {
        val itemToExchange = ProductItem(name = "Pomme", unit = ProductUnit.KILOGRAM, pricePerUnit = 2.0, quantity = 0.5)
        val exchangedAgainst = ExchangeItem(name = "Oignon", unit = ProductUnit.GRAM, pricePerUnit = 1.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 0.5)
        assertEquals(1000, result)
    }
}
