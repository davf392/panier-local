package com.davf392.panierlocal.core.designsystem.providers

import com.davf392.panierlocal.data.Product
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.features.basket.BasketUiState
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class BasketUiStatePreviewProvider : PreviewParameterProvider<BasketUiState> {
    override val values: Sequence<BasketUiState> = sequenceOf(
        BasketUiState(
            baskets = listOf(
                WeeklyBasketItem(
                    id = "TANDEM_LEGUMES_001",
                    distributionId = "dist-001",
                    formula = "Tandem",
                    expectedCount = 20,
                    actualCount = 15,
                    productsList = listOf(
                        ProductItem(
                            product = Product(
                                id = "1",
                                name = "Salade",
                                unit = ProductUnit.PIECE,
                                pricePerUnit = 2.50
                            ),
                            quantity = 1.0
                        ),
                        ProductItem(
                            product = Product(
                                id = "2",
                                name = "Concombre",
                                unit = ProductUnit.PIECE,
                                pricePerUnit = 1.80
                            ),
                            quantity = 1.0
                        ),
                        ProductItem(
                            product = Product(
                                id = "3",
                                name = "Oignon blanc",
                                unit = ProductUnit.GRAM,
                                pricePerUnit = 1.60
                            ),
                            quantity = 200.0
                        ),
                        ProductItem(
                            product = Product(
                                id = "4",
                                name = "Tomate",
                                unit = ProductUnit.GRAM,
                                pricePerUnit = 1.80
                            ),
                            quantity = 150.0
                        ),
                        ProductItem(
                            product = Product(
                                id = "5",
                                name = "Aubergine",
                                unit = ProductUnit.GRAM,
                                pricePerUnit = 3.20
                            ),
                            quantity = 800.0
                        )
                    )
                ),
                WeeklyBasketItem(
                    id = "SOLO_FRUITS_001",
                    distributionId = "dist-002",
                    formula = "Solo",
                    expectedCount = 30,
                    actualCount = 25,
                    productsList = listOf(
                        ProductItem(
                            product = Product(
                                id = "6",
                                name = "Banane",
                                unit = ProductUnit.GRAM,
                                pricePerUnit = 3.0
                            ),
                            quantity = 500.0
                        ),
                        ProductItem(
                            product = Product(
                                id = "7",
                                name = "Pomme",
                                unit = ProductUnit.KILOGRAM,
                                pricePerUnit = 3.50
                            ),
                            quantity = 1.0
                        ),
                        ProductItem(
                            product = Product(
                                id = "8",
                                name = "Poire",
                                unit = ProductUnit.GRAM,
                                pricePerUnit = 7.0
                            ),
                            quantity = 500.0
                        )
                    )
                )
            )
        )
    )
}