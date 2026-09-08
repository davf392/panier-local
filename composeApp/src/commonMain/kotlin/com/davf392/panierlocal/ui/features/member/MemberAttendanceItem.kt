package com.davf392.panierlocal.ui.features.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.data.member.MemberAttendance
import com.davf392.panierlocal.ui.CheckIcon
import com.davf392.panierlocal.ui.CloseIcon
import com.davf392.panierlocal.ui.RefreshIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun MemberAttendanceItem(
    attendance: MemberAttendance,
    onCollected: () -> Unit,
    onAbsent: () -> Unit,
    onReset: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${attendance.member.firstName} ${attendance.member.lastName}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = attendance.member.notes ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (attendance.status == AttendanceStatus.EXPECTED) {
                IconButton(onClick = onCollected) {
                    Icon(CheckIcon, contentDescription = "Récupéré", tint = Color.Green)
                }
                IconButton(onClick = onAbsent) {
                    Icon(CloseIcon, contentDescription = "Absent", tint = Color.Red)
                }
            } else {
                IconButton(onClick = onReset) {
                    Icon(RefreshIcon, contentDescription = "Annuler", tint = MaterialTheme.colorScheme.primary)
                }
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = attendance.status.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun MemberAttendanceItemPreview(
    @PreviewParameter(MemberAttendancePreviewParameterProvider::class) attendance: MemberAttendance
) {
    PanierLocalTheme {
        Surface {
            MemberAttendanceItem(
                attendance = attendance,
                onCollected = {},
                onAbsent = {},
                onReset = {}
            )
        }
    }
}

class MemberAttendancePreviewParameterProvider : PreviewParameterProvider<MemberAttendance> {
    override val values: Sequence<MemberAttendance> = sequenceOf(
        MemberAttendance(
            distributionId = "d1",
            member = Member(
                id = "m1",
                firstName = "Camille",
                lastName = "Benali",
                email = "camillebenali@example.com",
                needsRenewal = true,
                hasArrears = false,
                notes = "Cotisation à régulariser"
            ),
            basketFormulaIds = listOf("b1"),
            status = AttendanceStatus.EXPECTED
        ),
        MemberAttendance(
            distributionId = "d1",
            member = Member(
                id = "m1",
                firstName = "Yassin",
                lastName = "Traoré",
                email = "yassintraore@example.com",
                needsRenewal = true,
                hasArrears = false,
                notes = "Abonnement à renouveler"
            ),
            basketFormulaIds = listOf("b1"),
            status = AttendanceStatus.ABSENT
        ),
        MemberAttendance(
            distributionId = "d1",
            member = Member(
                id = "m1",
                firstName = "Léa",
                lastName = "Chen",
                email = "leachen@example.com",
                needsRenewal = true,
                hasArrears = false
            ),
            basketFormulaIds = listOf("b1"),
            status = AttendanceStatus.COLLECTED
        )
    )
}