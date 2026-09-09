package com.davf392.panierlocal.state

import com.davf392.panierlocal.data.member.MemberAttendance

data class MemberTrackingUiState(
    val attendances: List<MemberAttendance> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
) {
    val filteredAttendances: List<MemberAttendance>
        get() = if (searchQuery.isBlank()) {
            attendances
        } else {
            attendances.filter {
                it.member.firstName.contains(searchQuery, ignoreCase = true) ||
                        it.member.lastName.contains(searchQuery, ignoreCase = true)
            }
        }
}