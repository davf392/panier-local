package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.davf392.panierlocal.features.member.MemberTrackingScreen
import com.davf392.panierlocal.features.member.MemberTrackingViewModel
import com.davf392.panierlocal.repository.MockMemberRepository
import androidx.compose.runtime.remember

object MembersScreenVoyager : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val memberRepository = remember { MockMemberRepository() }
        val currentLocationId = "dist-001"
        val viewModel: MemberTrackingViewModel = viewModel(
            key = currentLocationId
        ) { MemberTrackingViewModel(memberRepository, currentLocationId) }
        val uiState by viewModel.uiState.collectAsState()
        
        MemberTrackingScreen(
            uiState = uiState,
            onCollected = viewModel::markAsCollected,
            onAbsent = viewModel::markAsAbsent,
            onReset = viewModel::resetStatus,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
            onMemberClick = { member ->
                navigator.push(MemberDetailsScreenVoyager(member.id))
            }
        )
    }
}
