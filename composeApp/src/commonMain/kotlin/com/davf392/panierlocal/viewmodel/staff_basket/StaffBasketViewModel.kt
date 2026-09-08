package com.davf392.panierlocal.viewmodel.staff_basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.repository.ProductRepository
import com.davf392.panierlocal.state.BasketUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StaffBasketViewModel(
    private val repository: ProductRepository = ProductRepository()
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

    fun updateActualCount(id: String, newCount: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                baskets = currentState.baskets.map { item ->
                    if (item.id == id) item.copy(actualCount = newCount)
                    else item
                }
            )
        }
    }

    private fun mapDistributionToLocationName(distributionId: String): String = when (distributionId) {
        "dist-001" -> "Le Croiseur (Lyon 7)"
        "dist-002" -> "Cabanes (Lyon 8)"
        else -> "Inconnu"
    }
}
