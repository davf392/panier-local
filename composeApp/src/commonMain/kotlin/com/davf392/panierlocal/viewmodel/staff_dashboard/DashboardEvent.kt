package com.davf392.panierlocal.viewmodel.staff_dashboard

import com.davf392.panierlocal.data.staff_dashboard.AlertPriority

sealed class DashboardEvent {
    data class ReportAlert(val message: String, val priority: AlertPriority) : DashboardEvent()
    data class ResolveAlert(val alertId: String) : DashboardEvent()
}
