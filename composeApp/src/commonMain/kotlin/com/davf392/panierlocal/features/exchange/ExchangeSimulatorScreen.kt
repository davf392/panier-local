package com.davf392.panierlocal.features.exchange

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ExchangeStep {
    SELECT_PRODUCT,
    CONFIRMATION
}

@Composable
fun ExchangeSimulatorScreen(
    uiState: ExchangeUiState,
    onProductSelected: (ExchangeItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(ExchangeStep.SELECT_PRODUCT) }

    val transitionSpec: AnimatedContentTransitionScope<ExchangeStep>.() -> ContentTransform = {
        fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        WeightDisplaySection(
            item = uiState.itemToExchange,
            weightGrams = uiState.returnedWeightGrams
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        AnimatedContent(
            targetState = step,
            transitionSpec = transitionSpec
        ) { targetStep ->
            when (targetStep) {
                ExchangeStep.SELECT_PRODUCT -> {
                    ProductSelectionSection(
                        availableProducts = uiState.availableProducts,
                        onProductSelected = { product ->
                            onProductSelected(product)
                            step = ExchangeStep.CONFIRMATION
                        }
                    )
                }
                ExchangeStep.CONFIRMATION -> {
                    uiState.selectedProduct?.let { selectedProduct ->
                        ResultDisplaySection(
                            exchangedProduct = selectedProduct,
                            maxWeightGrams = uiState.exchangeResult,
                            onModifySelection = { step = ExchangeStep.SELECT_PRODUCT }
                        )
                    }
                }
            }
        }
    }
}

// --- Previews ---

@Preview
@Composable
fun ExchangeSimulatorScreenLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ExchangeSimulatorScreen(
                uiState = PreviewUiState,
                onProductSelected = {},
            )
        }
    }
}

@Preview
@Composable
fun ExchangeSimulatorScreenDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ExchangeSimulatorScreen(
                uiState = PreviewUiState,
                onProductSelected = {},
            )
        }
    }
}

private val PreviewUiState = ExchangeUiState(
    itemToExchange = ProductItem(
        id = "6",
        name = "Banane",
        quantity = 500.0,
        unit = ProductUnit.PIECE,
        pricePerUnit = 3.0,
        totalPrice = 1.50
    ),
    availableProducts = listOf(
        ExchangeItem(id = "7", name = "Pomme", pricePerUnit = 3.50),
        ExchangeItem(id = "8", name = "Poire", pricePerUnit = 3.50)
    ),
    returnedWeightGrams = 150,
    selectedProduct = ExchangeItem(id = "7", name = "Pomme", pricePerUnit = 3.50),
    exchangeResult = 200
)