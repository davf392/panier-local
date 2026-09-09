package com.davf392.panierlocal.features.staff_dashboard

sealed class DashboardEvent {
    data class ReportAlert(val message: String, val priority: AlertPriority) : DashboardEvent()
    data class ResolveAlert(val alertId: String) : DashboardEvent()
}
