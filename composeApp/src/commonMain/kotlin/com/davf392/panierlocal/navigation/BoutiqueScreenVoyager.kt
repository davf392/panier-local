package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.davf392.panierlocal.features.boutique.BoutiqueScreen
import com.davf392.panierlocal.ui.catalog.CatalogViewModel
import com.davf392.panierlocal.ui.catalog.CatalogViewModelFactory

object BoutiqueScreenVoyager : Screen {
    @Composable
    override fun Content() {
        val socleoConfig = LocalSocleoConfig.current
        val viewModel: CatalogViewModel = viewModel(
            factory = CatalogViewModelFactory(socleoConfig = socleoConfig)
        )
        val uiState by viewModel.uiState.collectAsState()

        BoutiqueScreen(
            uiState = uiState,
            onRetry = { viewModel.loadBoutiqueProducts() }
        )
    }
}
