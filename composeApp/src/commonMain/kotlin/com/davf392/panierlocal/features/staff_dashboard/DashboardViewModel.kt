package com.davf392.panierlocal.features.staff_dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DashboardViewModel : ViewModel() {

    private val _distributions = MutableStateFlow<List<Distribution>>(emptyList())
    val distributions: StateFlow<List<Distribution>> = _distributions.asStateFlow()

    private val _selectedDistributionId = MutableStateFlow<String?>(null)
    val selectedDistributionId: StateFlow<String?> = _selectedDistributionId.asStateFlow()

    val uiState: StateFlow<DistributionUiState?> = combine(
        _distributions,
        _selectedDistributionId
    ) { distributions, selectedId ->
        distributions.find { it.id == selectedId } ?: distributions.firstOrNull()
    }.map { distribution ->
        distribution?.toUiState()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        loadMockDistributions()
    }

    fun selectDistribution(id: String) {
        _selectedDistributionId.value = id
    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.ReportAlert -> reportAlert(event.message, event.priority)
            is DashboardEvent.ResolveAlert -> resolveAlert(event.alertId)
        }
    }

    private fun reportAlert(message: String, priority: AlertPriority) {
        _distributions.value = _distributions.value.map { dist ->
            if (dist.id == _selectedDistributionId.value) {
                dist.copy(
                    alerts = dist.alerts + DashboardAlert(
                        id = Clock.System.now().toEpochMilliseconds().toString(),
                        message = message,
                        priority = priority
                    )
                )
            } else dist
        }
    }

    private fun resolveAlert(alertId: String) {
        _distributions.value = _distributions.value.map { dist ->
            if (dist.id == _selectedDistributionId.value) {
                dist.copy(alerts = dist.alerts.filter { it.id != alertId })
            } else dist
        }
    }

    private fun loadMockDistributions() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        
        val croiseurStart = LocalDateTime(today.year, today.month, today.dayOfMonth, 17, 30)
        val croiseurEnd = LocalDateTime(today.year, today.month, today.dayOfMonth, 19, 0)
        
        val cabanesStart = LocalDateTime(today.year, today.month, today.dayOfMonth, 17, 0)
        val cabanesEnd = LocalDateTime(today.year, today.month, today.dayOfMonth, 18, 30)

        val mockData = listOf(
            Distribution(
                "dist-001", croiseurStart, croiseurEnd,
                "Le Croiseur (Lyon 7)", DistributionStatus.PREPARATION,
                listOf(PermanenceSlot("p1", "Jean", "Responsable", true)),
                listOf(
                    BasketFormulaSummary("b1", "Mini", 20, 15),
                    BasketFormulaSummary("b2", "Solo", 30, 25),
                    BasketFormulaSummary("b3", "Tandem", 15, 10),
                    BasketFormulaSummary("b4", "Famille", 10, 8)
                ),
                listOf(DashboardAlert("a1", "Alerte Lyon 7", AlertPriority.INFO))
            ),
            Distribution(
                "dist-002", cabanesStart, cabanesEnd,
                "Cabanes (Lyon 8)", DistributionStatus.PREPARATION,
                listOf(PermanenceSlot("p2", "Marie", "Responsable", true)),
                listOf(
                    BasketFormulaSummary("b1", "Mini", 25, 25),
                    BasketFormulaSummary("b2", "Solo", 35, 30),
                    BasketFormulaSummary("b3", "Tandem", 20, 18),
                    BasketFormulaSummary("b4", "Famille", 12, 12)
                ),
                emptyList()
            )
        )
        _distributions.value = mockData
        _selectedDistributionId.value = mockData.first().id
    }

    private fun Distribution.toUiState(): DistributionUiState {
        return DistributionUiState(
            id = id,
            location = location,
            startTime = startTime,
            endTime = endTime,
            permanenceSlots = permanenceSlots,
            basketSummaries = basketSummaries,
            alerts = alerts
        )
    }
}
