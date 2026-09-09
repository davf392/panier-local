package com.davf392.panierlocal.features.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.repository.MockProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StaffBasketViewModel(
    private val repository: MockProductRepository = MockProductRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(BasketUiState())
    val uiState: StateFlow<BasketUiState> = _uiState.asStateFlow()

    init {
        fetchBaskets()
    }

    private fun fetchBaskets() {
        viewModelScope.launch {
            val baskets = repository.getWeeklyBasketList()
            _uiState.update { currentState ->
                val newLocation = baskets.firstOrNull()?.distributionId?.let(::mapDistributionToLocationName)

                currentState.copy(
                    baskets = baskets,
                    locationName = newLocation ?: currentState.locationName
                )
            }
        }
    }

    private fun mapDistributionToLocationName(distributionId: String): String = when (distributionId) {
        "dist-001" -> "Le Croiseur (Lyon 7)"
        "dist-002" -> "Cabanes (Lyon 8)"
        else -> "Inconnu"
    }
}
