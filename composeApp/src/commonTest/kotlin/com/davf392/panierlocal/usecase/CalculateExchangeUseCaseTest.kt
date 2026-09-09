package com.davf392.panierlocal.usecase

import com.davf392.panierlocal.data.Product
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.features.exchange.CalculateExchangeUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateExchangeUseCaseTest {

    private val useCase = CalculateExchangeUseCase()

    @Test
    fun `test exchange same gram unit`() {
        val itemToExchange = ProductItem(Product("1", "Oignon", ProductUnit.GRAM, 1.0), 500.0)
        val exchangedAgainst = Product("2", "Tomate", ProductUnit.GRAM, 1.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 500.0)
        assertEquals(500, result)
    }

    @Test
    fun `test exchange gram to piece`() {
        val itemToExchange = ProductItem(Product("1", "Oignon", ProductUnit.GRAM, 2.0), 500.0)
        val exchangedAgainst = Product("2", "Salade", ProductUnit.PIECE, 0.5)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 500.0)
        assertEquals(2, result)
    }

    @Test
    fun `test exchange piece to kilogram`() {
        val itemToExchange = ProductItem(Product("1", "Concombre", ProductUnit.PIECE, 2.0), 1.0)
        val exchangedAgainst = Product("2", "Pomme", ProductUnit.KILOGRAM, 4.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 1.0)
        assertEquals(500, result)
    }

    @Test
    fun `test insufficient value`() {
        val itemToExchange = ProductItem(Product("1", "Oignon", ProductUnit.GRAM, 1.0), 100.0)
        val exchangedAgainst = Product("2", "Salade", ProductUnit.PIECE, 2.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 100.0)
        assertEquals(0, result)
    }

    @Test
    fun `test exchange exact value`() {
        val itemToExchange = ProductItem(Product("1", "Produit", ProductUnit.GRAM, 2.0), 500.0)
        val exchangedAgainst = Product("2", "Autre", ProductUnit.KILOGRAM, 2.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 500.0)
        assertEquals(500, result)
    }

    @Test
    fun `test zero price handling`() {
        val itemToExchange = ProductItem(Product("1", "Gratuit", ProductUnit.GRAM, 0.0), 100.0)
        val exchangedAgainst = Product("2", "Pomme", ProductUnit.KILOGRAM, 2.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 100.0)
        assertEquals(0, result)
    }

    @Test
    fun `test very large quantity`() {
        val itemToExchange = ProductItem(Product("1", "Blé", ProductUnit.GRAM, 0.001), 1000000.0)
        val exchangedAgainst = Product("2", "Pomme", ProductUnit.GRAM, 1.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 1000000.0)
        assertEquals(1000, result)
    }

    @Test
    fun `test exchange kilogram to piece`() {
        val itemToExchange = ProductItem(Product("1", "Pomme", ProductUnit.KILOGRAM, 2.0), 1.0)
        val exchangedAgainst = Product("2", "Salade", ProductUnit.PIECE, 1.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 1.0)
        assertEquals(2, result)
    }

    @Test
    fun `test exchange gram to kilogram`() {
        val itemToExchange = ProductItem(Product("1", "Oignon", ProductUnit.GRAM, 1.0), 500.0)
        val exchangedAgainst = Product("2", "Pomme", ProductUnit.KILOGRAM, 2.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 500.0)
        assertEquals(250, result)
    }

    @Test
    fun `test exchange piece to gram`() {
        val itemToExchange = ProductItem(Product("1", "Concombre", ProductUnit.PIECE, 2.0), 1.0)
        val exchangedAgainst = Product("2", "Oignon", ProductUnit.GRAM, 4.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 1.0)
        assertEquals(500, result)
    }

    @Test
    fun `test exchange kilogram to gram`() {
        val itemToExchange = ProductItem(Product("1", "Pomme", ProductUnit.KILOGRAM, 2.0), 0.5)
        val exchangedAgainst = Product("2", "Oignon", ProductUnit.GRAM, 1.0)
        val result = useCase.execute(itemToExchange, exchangedAgainst, 0.5)
        assertEquals(1000, result)
    }
}
