package com.davf392.panierlocal.viewmodel.staff_dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.staff_dashboard.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel : ViewModel() {

    private val _distributions = MutableStateFlow<List<Distribution>>(emptyList())
    val distributions: StateFlow<List<Distribution>> = _distributions.asStateFlow()

    private val _selectedDistributionId = MutableStateFlow<String?>(null)
    val selectedDistributionId: StateFlow<String?> = _selectedDistributionId.asStateFlow()

    val uiState: StateFlow<Distribution?> = combine(
        _distributions,
        _selectedDistributionId
    ) { distributions, selectedId ->
        distributions.find { it.id == selectedId } ?: distributions.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        loadMockDistributions()
    }

    fun selectDistribution(id: String) {
        _selectedDistributionId.value = id
    }

    private fun loadMockDistributions() {
        val mockData = listOf(
            Distribution(
                "dist-001", Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()), 
                "Le Croiseur (Lyon 7)", DistributionStatus.PREPARATION,
                listOf(PermanenceSlot("p1", "Jean", "Responsable", true)),
                listOf(BasketFormulaSummary("b1", "Mini", 20, 15)),
                listOf(DashboardAlert("a1", "Alerte Lyon 7", AlertPriority.INFO))
            ),
            Distribution(
                "dist-002", Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()), 
                "Cabanes (Lyon 8)", DistributionStatus.PREPARATION,
                listOf(PermanenceSlot("p2", "Marie", "Responsable", true)),
                listOf(BasketFormulaSummary("b2", "Solo", 30, 25)),
                emptyList()
            )
        )
        _distributions.value = mockData
        _selectedDistributionId.value = mockData.first().id
    }
}
