package com.davf392.panierlocal.viewmodel.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.repository.IMemberRepository
import com.davf392.panierlocal.state.MemberTrackingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
                _uiState.value = _uiState.value.copy(attendances = list, isLoading = false)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
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
