package com.davf392.panierlocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davf392.panierlocal.repository.ProductRepository
import com.davf392.panierlocal.state.BasketUiState
import com.davf392.panierlocal.state.ExchangeUiState
import com.davf392.panierlocal.ui.navigation.Routes
import com.davf392.panierlocal.ui.components.screens.BasketScreen
import com.davf392.panierlocal.ui.components.screens.ExchangeSimulatorScreen
import com.davf392.panierlocal.ui.navigation.rememberAppNavController
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.usecase.CalculateExchangeUseCase
import com.davf392.panierlocal.viewmodel.BasketViewModel
import com.davf392.panierlocal.viewmodel.BasketViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            PanierLocalApp()
        }
    }
}

@Composable
fun PanierLocalApp() {
    PanierLocalTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            val basketViewModel = viewModel<BasketViewModel>(
                factory = BasketViewModelFactory(
                    productRepository = ProductRepository(),
                    calculateExchangeUseCase = CalculateExchangeUseCase()
                )
            )

            NavHost(navController = navController, startDestination = Routes.WEEKLY_BASKET) {
                composable(Routes.WEEKLY_BASKET) {
                    BasketScreen(
                        uiState = basketViewModel.uiState.collectAsState().value,
                        onViewHistoryClicked = { navController.navigate(Routes.BASKET_HISTORY) },
                        onExchangeClicked = { productItem ->
                            navController.navigate("${Routes.EXCHANGE_SIMULATOR}/${productItem.id}")
                        }
                    )
                }
                composable(route = "${Routes.EXCHANGE_SIMULATOR}/{itemId}") { backStackEntry ->
                    basketViewModel.navigateToExchangeSimulatorScreen(
                        itemId = backStackEntry.arguments?.getString("itemId")
                    )
                    ExchangeSimulatorScreen(
                        uiState = basketViewModel.exchangeUiState.collectAsState().value,
                        onProductSelected = { product -> basketViewModel.selectProduct(product) },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}