package com.davf392.panierlocal.viewmodel.member

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
    val isLoading: Boolean = true
)

class MemberDetailsViewModel(
    private val memberRepository: IMemberRepository,
    private val productRepository: IProductRepository,
    private val memberId: String
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
            
            _uiState.value = MemberDetailsUiState(
                member = member, 
                formulas = allFormulas, // Just loading all for display as placeholder
                isLoading = false
            )
        }
    }

    fun updateNotes(notes: String) {
        viewModelScope.launch {
            memberRepository.updateMemberNotes(memberId, notes)
        }
    }
}
