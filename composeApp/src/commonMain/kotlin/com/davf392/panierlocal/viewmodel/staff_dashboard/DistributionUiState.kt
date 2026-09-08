package com.davf392.panierlocal.viewmodel.staff_dashboard

import com.davf392.panierlocal.data.staff_dashboard.*
import kotlinx.datetime.LocalDateTime

data class DistributionUiState(
    val id: String,
    val location: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val permanenceSlots: List<PermanenceSlot>,
    val basketSummaries: List<BasketFormulaSummary>,
    val alerts: List<DashboardAlert>
) {
    companion object {
        fun empty(): DistributionUiState {
            return DistributionUiState(
                id = "",
                location = "",
                startTime = LocalDateTime(2026, 1, 1, 0, 0),
                endTime = LocalDateTime(2026, 1, 1, 0, 0),
                permanenceSlots = emptyList(),
                basketSummaries = emptyList(),
                alerts = emptyList()
            )
        }
    }
}
