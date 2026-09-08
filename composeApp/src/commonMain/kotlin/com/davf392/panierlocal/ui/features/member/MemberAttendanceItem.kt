package com.davf392.panierlocal.ui.features.member

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.davf392.panierlocal.data.member.MemberAttendance
import com.davf392.panierlocal.data.member.toFrench
import com.davf392.panierlocal.ui.CheckIcon
import com.davf392.panierlocal.ui.CloseIcon
import com.davf392.panierlocal.ui.RefreshIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun MemberAttendanceItem(
    attendance: MemberAttendance,
    onCollected: () -> Unit,
    onAbsent: () -> Unit,
    onReset: () -> Unit,
    onMemberClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onMemberClick)
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
                if (!attendance.member.notes.isNullOrBlank()) {
                    Text(
                        text = attendance.member.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Row(
                modifier = Modifier.padding(start = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (attendance.status == AttendanceStatus.EXPECTED) {
                    IconButton(onClick = onCollected) {
                        Icon(CheckIcon, contentDescription = "Récupéré", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onAbsent) {
                        Icon(CloseIcon, contentDescription = "Absent", tint = MaterialTheme.colorScheme.error)
                    }
                } else {
                    IconButton(onClick = onReset) {
                        Icon(RefreshIcon, contentDescription = "Annuler", tint = MaterialTheme.colorScheme.primary)
                    }
                    // Container with fixed width for badge to ensure constant alignment of the Reset button
                    Box(
                        modifier = Modifier.width(80.dp), 
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = when (attendance.status) {
                                AttendanceStatus.COLLECTED -> Color.Green.copy(alpha = 0.2f)
                                AttendanceStatus.ABSENT -> Color.Red.copy(alpha = 0.2f)
                                else -> Color.Gray.copy(alpha = 0.2f)
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                text = attendance.status.toFrench(),
                                style = MaterialTheme.typography.labelSmall,
                                maxLines = 1
                            )
                        }
                    }
                }
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
                onReset = {},
                onMemberClick = {}
            )
        }
    }
}
