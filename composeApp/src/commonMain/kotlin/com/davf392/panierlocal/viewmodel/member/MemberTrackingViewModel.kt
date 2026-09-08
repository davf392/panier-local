package com.davf392.panierlocal.viewmodel.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.data.member.MemberAttendance
import com.davf392.panierlocal.repository.IMemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MemberTrackingUiState(
    val attendances: List<MemberAttendance> = emptyList(),
    val isLoading: Boolean = false
)

class MemberTrackingViewModel(
    private val repository: IMemberRepository,
    private val distributionId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemberTrackingUiState(isLoading = true))
    val uiState: StateFlow<MemberTrackingUiState> = _uiState.asStateFlow()

    init {
        loadAttendances()
    }

    private fun loadAttendances() {
        viewModelScope.launch {
            repository.getAttendancesForDistribution(distributionId).collect { list ->
                _uiState.value = MemberTrackingUiState(attendances = list, isLoading = false)
            }
        }
    }

    fun markAsCollected(memberId: String) {
        updateStatus(memberId, AttendanceStatus.COLLECTED)
    }

    fun markAsAbsent(memberId: String) {
        updateStatus(memberId, AttendanceStatus.ABSENT)
    }

    fun resetStatus(memberId: String) {
        updateStatus(memberId, AttendanceStatus.EXPECTED)
    }

    private fun updateStatus(memberId: String, status: AttendanceStatus) {
        viewModelScope.launch {
            repository.updateAttendanceStatus(memberId, distributionId, status)
        }
    }
}
