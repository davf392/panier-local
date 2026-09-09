package com.davf392.panierlocal.features.staff_dashboard

enum class AlertPriority {
    INFO, WARNING, CRITICAL
}

data class DashboardAlert(
    val id: String,
    val message: String,
    val priority: AlertPriority
)
