package com.davf392.panierlocal.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.BoutiqueProduct
import com.davf392.panierlocal.data.CatalogItem
import com.davf392.panierlocal.repository.CatalogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(private val repository: CatalogRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        loadBoutiqueProducts()
    }

    fun loadBoutiqueProducts() {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading
            try {
                val products = repository.getBoutiqueProducts()
                _uiState.value = CatalogUiState.Success(
                    items = products,
                    boutiqueProducts = products
                )
            } catch (e: Exception) {
                _uiState.value = CatalogUiState.Error(
                    message = e.message ?: "Une erreur est survenue lors de la récupération des produits."
                )
            }
        }
    }

    fun loadCatalog(startDate: String, endDate: String) {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading
            try {
                val items = repository.getCatalog(startDate, endDate)
                _uiState.value = CatalogUiState.Success(
                    items = items,
                    boutiqueProducts = items.filterIsInstance<BoutiqueProduct>()
                )
            } catch (e: Exception) {
                _uiState.value = CatalogUiState.Error(
                    message = e.message ?: "Une erreur est survenue"
                )
            }
        }
    }
}

sealed interface CatalogUiState {
    object Loading : CatalogUiState
    data class Success(
        val items: List<CatalogItem>,
        val boutiqueProducts: List<BoutiqueProduct> = items.filterIsInstance<BoutiqueProduct>()
    ) : CatalogUiState
    data class Error(val message: String) : CatalogUiState
}
