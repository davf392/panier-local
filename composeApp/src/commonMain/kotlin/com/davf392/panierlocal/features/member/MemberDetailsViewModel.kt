package com.davf392.panierlocal.features.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.repository.IMemberRepository
import com.davf392.panierlocal.repository.IProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MemberDetailsUiState(
    val member: Member? = null,
    val formulas: List<WeeklyBasketItem> = emptyList(),
    val attendanceStatus: com.davf392.panierlocal.data.member.AttendanceStatus? = null,
    val isLoading: Boolean = true
)

class MemberDetailsViewModel(
    private val memberRepository: IMemberRepository,
    private val productRepository: IProductRepository,
    private val memberId: String,
    private val distributionId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemberDetailsUiState())
    val uiState: StateFlow<MemberDetailsUiState> = _uiState.asStateFlow()

    init {
        loadMemberDetails()
    }

    private fun loadMemberDetails() {
        viewModelScope.launch {
            val member = memberRepository.getMemberById(memberId)
            val allFormulas = productRepository.getWeeklyBasketList()

            memberRepository.getAttendancesForDistribution(distributionId).collect { attendances ->
                val attendance = attendances.find { it.member.id == memberId }
                val userFormulaIds = attendance?.basketFormulaIds ?: emptyList()
                val userFormulas = allFormulas.filter { it.id in userFormulaIds }

                _uiState.value = _uiState.value.copy(
                    member = member,
                    formulas = userFormulas,
                    attendanceStatus = attendance?.status,
                    isLoading = false
                )
            }
        }
    }

    fun markAsCollected() {
        updateAttendance(com.davf392.panierlocal.data.member.AttendanceStatus.COLLECTED)
    }

    fun markAsAbsent() {
        updateAttendance(com.davf392.panierlocal.data.member.AttendanceStatus.ABSENT)
    }

    fun resetStatus() {
        updateAttendance(com.davf392.panierlocal.data.member.AttendanceStatus.EXPECTED)
    }

    private fun updateAttendance(status: com.davf392.panierlocal.data.member.AttendanceStatus) {
        viewModelScope.launch {
            memberRepository.updateAttendanceStatus(memberId, distributionId, status)
        }
    }

    fun updateNotes(notes: String) {
        viewModelScope.launch {
            memberRepository.updateMemberNotes(memberId, notes)
        }
    }
}
