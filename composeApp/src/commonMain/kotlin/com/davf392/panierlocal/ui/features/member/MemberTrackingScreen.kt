package com.davf392.panierlocal.ui.features.member

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.data.member.MemberAttendance
import com.davf392.panierlocal.ui.composition.LocalDistributionContext
import com.davf392.panierlocal.ui.features.dashboard.components.DistributionHeaderCard
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.viewmodel.member.MemberTrackingUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MemberTrackingScreen(
    uiState: MemberTrackingUiState,
    onCollected: (String) -> Unit = {},
    onAbsent: (String) -> Unit = {},
    onReset: (String) -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onMemberClick: (Member) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val distributionContext = LocalDistributionContext.current
    
    Column(modifier = modifier.fillMaxSize()) {
        if (distributionContext != null) {
            DistributionHeaderCard(
                startTime = distributionContext.startTime,
                endTime = distributionContext.endTime,
                currentLocation = distributionContext.currentLocation,
                locations = distributionContext.locations,
                onLocationSelected = distributionContext.onLocationSelected,
                modifier = Modifier.padding(16.dp)
            )
        }
        MemberTrackingScreenContent(
            uiState = uiState,
            onCollected = onCollected,
            onAbsent = onAbsent,
            onReset = onReset,
            onSearchQueryChanged = onSearchQueryChanged,
            onMemberClick = onMemberClick
        )
    }
}

@Composable
fun MemberTrackingScreenContent(
    uiState: MemberTrackingUiState,
    onCollected: (String) -> Unit = {},
    onAbsent: (String) -> Unit = {},
    onReset: (String) -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onMemberClick: (Member) -> Unit = {}
) {
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                label = { Text("Rechercher un adhérent") },
                singleLine = true
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.filteredAttendances) { attendance ->
                    MemberAttendanceItem(
                        attendance = attendance,
                        onCollected = { onCollected(attendance.member.id) },
                        onAbsent = { onAbsent(attendance.member.id) },
                        onReset = { onReset(attendance.member.id) },
                        onMemberClick = { onMemberClick(attendance.member) }
                    )
                }
            }
        }
    }
}

// --- Previews ---

@Preview
@Composable
private fun MemberTrackingScreenLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            MemberTrackingScreenContent(uiState = MockUiState)
        }
    }
}

@Preview
@Composable
private fun MemberTrackingScreenDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            MemberTrackingScreenContent(uiState = MockUiState)
        }
    }
}

private val MockUiState = MemberTrackingUiState(
    attendances = listOf(
        MemberAttendance(
            "d1",
            Member("m1", "Alice", "Dupont", "alice@example.com", "067733445522", false, false),
            listOf("b1"),
            AttendanceStatus.EXPECTED
        ),
        MemberAttendance(
            "d1",
            Member("m2", "Bob", "Martin", "bob@example.com", "067788993322", true, false),
            listOf("b2"),
            AttendanceStatus.EXPECTED
        )
    ),
    isLoading = false
)
