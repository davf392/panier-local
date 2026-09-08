package com.davf392.panierlocal.viewmodel.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.repository.IMemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MemberDetailsUiState(
    val member: Member? = null,
    val isLoading: Boolean = true
)

class MemberDetailsViewModel(
    private val repository: IMemberRepository,
    private val memberId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemberDetailsUiState())
    val uiState: StateFlow<MemberDetailsUiState> = _uiState.asStateFlow()

    init {
        loadMember()
    }

    private fun loadMember() {
        viewModelScope.launch {
            val member = repository.getMemberById(memberId)
            _uiState.value = MemberDetailsUiState(member = member, isLoading = false)
        }
    }

    fun updateNotes(notes: String) {
        viewModelScope.launch {
            repository.updateMemberNotes(memberId, notes)
        }
    }
}
