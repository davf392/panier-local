package com.davf392.panierlocal.ui.features.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MemberAttendanceItem(
    attendance: MemberAttendance,
    onCollected: () -> Unit,
    onAbsent: () -> Unit
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
                if (attendance.member.needsRenewal || attendance.member.hasArrears) {
                    Text(
                        text = "Cotisation à régulariser",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (attendance.status == AttendanceStatus.EXPECTED) {
                IconButton(onClick = onCollected) {
                    Icon(CheckIcon, contentDescription = "Récupéré", tint = Color.Green)
                }
                IconButton(onClick = onAbsent) {
                    Icon(CloseIcon, contentDescription = "Absent", tint = Color.Red)
                }
            } else {
                Text(
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
private fun MemberAttendanceItemPreview() {
    MemberAttendanceItem(
        attendance = MemberAttendance(
            "d1",
            Member("m1", "Alice", "Dupont", "alice@example.com", true, false),
            listOf("b1"),
            AttendanceStatus.EXPECTED
        ),
        onCollected = {},
        onAbsent = {}
    )
}
