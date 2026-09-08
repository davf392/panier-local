package com.davf392.panierlocal.ui.features.common.providers

import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.state.BasketUiState
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
                            id = "1",
                            name = "Salade",
                            quantity = 1.0,
                            unit = ProductUnit.PIECE,
                            pricePerUnit = 2.50,
                            totalPrice = 2.50,
                        ),
                        ProductItem(
                            id = "2",
                            name = "Concombre",
                            quantity = 1.0,
                            unit = ProductUnit.PIECE,
                            pricePerUnit = 1.80,
                            totalPrice = 1.80,
                        ),
                        ProductItem(
                            id = "3",
                            name = "Oignon blanc",
                            quantity = 200.0,
                            unit = ProductUnit.GRAM,
                            pricePerUnit = 1.60,
                            totalPrice = 1.60,
                        ),
                        ProductItem(
                            id = "4",
                            name = "Tomate",
                            quantity = 150.0,
                            unit = ProductUnit.GRAM,
                            pricePerUnit = 1.80,
                            totalPrice = 1.80,
                        ),
                        ProductItem(
                            id = "5",
                            name = "Aubergine",
                            quantity = 800.0,
                            unit = ProductUnit.GRAM,
                            pricePerUnit = 3.20,
                            totalPrice = 3.20,
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
                            id = "6",
                            name = "Banane",
                            quantity = 500.0,
                            unit = ProductUnit.GRAM,
                            pricePerUnit = 3.0,
                            totalPrice = 1.50,
                        ),
                        ProductItem(
                            id = "7",
                            name = "Pomme",
                            quantity = 1.0,
                            unit = ProductUnit.KILOGRAM,
                            pricePerUnit = 3.50,
                            totalPrice = 3.50,
                        ),
                        ProductItem(
                            id = "8",
                            name = "Poire",
                            quantity = 500.0,
                            unit = ProductUnit.GRAM,
                            pricePerUnit = 7.0,
                            totalPrice = 3.50,
                        )
                    )
                )
            )
        )
    )
}