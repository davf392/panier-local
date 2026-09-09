package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.features.member.MemberDetailsScreen
import com.davf392.panierlocal.features.member.MemberDetailsViewModel
import com.davf392.panierlocal.repository.MockMemberRepository
import com.davf392.panierlocal.repository.MockProductRepository

data class MemberDetailsScreenVoyager(val memberId: String) : Screen {
    @Composable
    override fun Content() {
        val memberRepository = remember { MockMemberRepository() }
        val productRepository = remember { MockProductRepository() }
        val currentLocationId = "dist-001"

        val viewModel: MemberDetailsViewModel = viewModel {
            MemberDetailsViewModel(memberRepository, productRepository, memberId, currentLocationId)
        }
        val uiState by viewModel.uiState.collectAsState()
        
        if (!uiState.isLoading) {
            uiState.member?.let { member ->
                MemberDetailsScreen(
                    member = member,
                    formulas = uiState.formulas,
                    status = uiState.attendanceStatus ?: AttendanceStatus.EXPECTED,
                    onCollected = viewModel::markAsCollected,
                    onAbsent = viewModel::markAsAbsent,
                    onReset = viewModel::resetStatus,
                    onSaveNotes = viewModel::updateNotes
                )
            }
        }
    }
}
