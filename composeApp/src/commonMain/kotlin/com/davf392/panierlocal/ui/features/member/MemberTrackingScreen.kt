package com.davf392.panierlocal.ui.features.member

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.data.member.MemberAttendance
import com.davf392.panierlocal.ui.composition.LocalDistributionContext
import com.davf392.panierlocal.ui.features.common.DistributionLocationHeader
import com.davf392.panierlocal.viewmodel.member.MemberTrackingUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MemberTrackingScreen(
    uiState: MemberTrackingUiState,
    onCollected: (String) -> Unit,
    onAbsent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val distributionContext = LocalDistributionContext.current
    
    Column(modifier = modifier.fillMaxSize()) {
        if (distributionContext != null) {
            DistributionLocationHeader(
                currentLocation = distributionContext.currentLocation,
                locations = distributionContext.locations,
                onLocationSelected = distributionContext.onLocationSelected
            )
        }
        MemberTrackingScreenContent(
            uiState = uiState,
            onCollected = onCollected,
            onAbsent = onAbsent
        )
    }
}

@Composable
fun MemberTrackingScreenContent(
    uiState: MemberTrackingUiState,
    onCollected: (String) -> Unit,
    onAbsent: (String) -> Unit
) {
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.attendances) { attendance ->
                MemberAttendanceItem(
                    attendance = attendance,
                    onCollected = { onCollected(attendance.member.id) },
                    onAbsent = { onAbsent(attendance.member.id) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MemberTrackingScreenPreview() {
    val mockUiState = MemberTrackingUiState(
        attendances = listOf(
            MemberAttendance(
                "d1",
                Member("m1", "Alice", "Dupont", "alice@example.com", false, false),
                listOf("b1"),
                AttendanceStatus.EXPECTED
            ),
            MemberAttendance(
                "d1",
                Member("m2", "Bob", "Martin", "bob@example.com", true, false),
                listOf("b2"),
                AttendanceStatus.EXPECTED
            )
        ),
        isLoading = false
    )
    MemberTrackingScreenContent(
        uiState = mockUiState,
        onCollected = {},
        onAbsent = {}
    )
}
