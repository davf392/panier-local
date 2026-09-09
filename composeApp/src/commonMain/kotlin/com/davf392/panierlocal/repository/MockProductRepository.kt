package com.davf392.panierlocal.repository

import com.davf392.panierlocal.data.Product
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.data.WeeklyBasketItem

class MockProductRepository: IProductRepository {

    override suspend fun getWeeklyBasketList(): List<WeeklyBasketItem> {
        return listOf(
            // Légumes
            WeeklyBasketItem("v1", "dist-001", "Mini", "Légumes", 20, 15, listOf(
                ProductItem(Product("p1", "Salade", ProductUnit.PIECE, 2.50), 1.0),
                ProductItem(Product("p2", "Carottes", ProductUnit.KILOGRAM, 2.00), 1.0)
            )),
            WeeklyBasketItem("v2", "dist-001", "Solo", "Légumes", 30, 25, listOf(
                ProductItem(Product("p1", "Salade", ProductUnit.PIECE, 2.50), 1.0),
                ProductItem(Product("p2", "Carottes", ProductUnit.KILOGRAM, 2.00), 1.0),
                ProductItem(Product("p3", "Courgettes", ProductUnit.KILOGRAM, 3.00), 1.0)
            )),
            WeeklyBasketItem("v3", "dist-001", "Tandem", "Légumes", 15, 10, listOf(
                ProductItem(Product("p1", "Salade", ProductUnit.PIECE, 2.50), 2.0),
                ProductItem(Product("p2", "Carottes", ProductUnit.KILOGRAM, 2.00), 1.5),
                ProductItem(Product("p3", "Courgettes", ProductUnit.KILOGRAM, 3.00), 1.0)
            )),
            WeeklyBasketItem("v4", "dist-001", "Famille", "Légumes", 10, 8, listOf(
                ProductItem(Product("p1", "Salade", ProductUnit.PIECE, 2.50), 3.0),
                ProductItem(Product("p2", "Carottes", ProductUnit.KILOGRAM, 2.00), 2.0),
                ProductItem(Product("p3", "Courgettes", ProductUnit.KILOGRAM, 3.00), 2.0)
            )),
            // Fruits
            WeeklyBasketItem("f1", "dist-001", "Mini", "Fruits", 15, 12, listOf(
                ProductItem(Product("p4", "Pommes", ProductUnit.KILOGRAM, 3.00), 1.0)
            )),
            WeeklyBasketItem("f2", "dist-001", "Solo", "Fruits", 20, 18, listOf(
                ProductItem(Product("p4", "Pommes", ProductUnit.KILOGRAM, 3.00), 1.0),
                ProductItem(Product("p5", "Bananes", ProductUnit.KILOGRAM, 2.50), 1.0)
            )),
            WeeklyBasketItem("f3", "dist-001", "Tandem", "Fruits", 10, 9, listOf(
                ProductItem(Product("p4", "Pommes", ProductUnit.KILOGRAM, 3.00), 2.0),
                ProductItem(Product("p5", "Bananes", ProductUnit.KILOGRAM, 2.50), 1.0),
                ProductItem(Product("p6", "Poires", ProductUnit.KILOGRAM, 4.00), 1.0)
            )),
            WeeklyBasketItem("f4", "dist-001", "Famille", "Fruits", 5, 5, listOf(
                ProductItem(Product("p4", "Pommes", ProductUnit.KILOGRAM, 3.00), 3.0),
                ProductItem(Product("p5", "Bananes", ProductUnit.KILOGRAM, 2.50), 2.0),
                ProductItem(Product("p6", "Poires", ProductUnit.KILOGRAM, 4.00), 2.0)
            )),
            // Autres
            WeeklyBasketItem("o1", "dist-001", "Chèvre", "Autres", 20, 18, listOf(ProductItem(Product("p7", "Chèvre frais", ProductUnit.PIECE, 4.0), 1.0))),
            WeeklyBasketItem("o2", "dist-001", "Vache", "Autres", 20, 18, listOf(ProductItem(Product("p8", "Tomme de vache", ProductUnit.PIECE, 6.0), 1.0))),
            WeeklyBasketItem("o3", "dist-001", "Crème", "Autres", 15, 15, listOf(ProductItem(Product("p9", "Crème", ProductUnit.PIECE, 2.0), 1.0))),
            WeeklyBasketItem("o4", "dist-001", "Beurre", "Autres", 15, 15, listOf(ProductItem(Product("p10", "Beurre", ProductUnit.PIECE, 3.0), 1.0))),
            WeeklyBasketItem("o5", "dist-001", "Yaourts", "Autres", 40, 35, listOf(ProductItem(Product("p11", "Yaourts x4", ProductUnit.PIECE, 2.5), 1.0))),
            WeeklyBasketItem("o6", "dist-001", "Pain", "Autres", 25, 25, listOf(ProductItem(Product("p12", "Pain complet", ProductUnit.PIECE, 3.5), 1.0))),
            WeeklyBasketItem("o7", "dist-001", "Oeufs", "Autres", 50, 48, listOf(ProductItem(Product("p13", "Oeufs x6", ProductUnit.PIECE, 2.0), 1.0)))
        )
    }

    override suspend fun getProductById(id: String): ProductItem? {
        return getWeeklyBasketList().flatMap { it.productsList }.find { it.product.id == id }
    }

    override suspend fun getAvailableProductsForExchange(selectedItem: ProductItem): List<Product> {
        return listOf(
            Product(id = "1", name = "Salade", unit = ProductUnit.PIECE, pricePerUnit = 2.50),
            Product(id = "2", name = "Concombre", unit = ProductUnit.PIECE, pricePerUnit = 1.80),
            Product(id = "3", name = "Oignon blanc", unit = ProductUnit.GRAM, pricePerUnit = 1.60),
            Product(id = "4", name = "Tomate", unit = ProductUnit.GRAM, pricePerUnit = 1.80),
            Product(id = "5", name = "Aubergine", unit = ProductUnit.GRAM, pricePerUnit = 3.20),
            Product(id = "6", name = "Banane", unit = ProductUnit.GRAM, pricePerUnit = 3.0),
            Product(id = "7", name = "Pomme", unit = ProductUnit.KILOGRAM, pricePerUnit = 3.50),
            Product(id = "8", name = "Poire", unit = ProductUnit.GRAM, pricePerUnit = 7.0),
        )
            .filter { item -> item.name != selectedItem.product.name }
    }
}
