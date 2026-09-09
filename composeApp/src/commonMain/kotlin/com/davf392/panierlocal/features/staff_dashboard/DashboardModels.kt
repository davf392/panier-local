package com.davf392.panierlocal.features.staff_dashboard

import kotlinx.datetime.LocalDateTime

enum class DistributionStatus {
    PREPARATION, IN_PROGRESS, COMPLETED
}

data class Distribution(
    val id: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val location: String,
    val status: DistributionStatus,
    val permanenceSlots: List<PermanenceSlot>,
    val basketSummaries: List<BasketFormulaSummary>,
    val alerts: List<DashboardAlert>
)
