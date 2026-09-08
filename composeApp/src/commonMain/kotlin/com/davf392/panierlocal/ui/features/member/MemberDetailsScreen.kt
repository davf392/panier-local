package com.davf392.panierlocal.ui.features.member

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.data.member.toFrench
import com.davf392.panierlocal.ui.CheckIcon
import com.davf392.panierlocal.ui.CloseIcon
import com.davf392.panierlocal.ui.MailIcon
import com.davf392.panierlocal.ui.PhoneIcon
import com.davf392.panierlocal.ui.RefreshIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun MemberDetailsScreen(
    member: Member,
    formulas: List<WeeklyBasketItem>,
    status: AttendanceStatus,
    onCollected: () -> Unit = {},
    onAbsent: () -> Unit = {},
    onReset: () -> Unit = {},
    onSaveNotes: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var notes by remember(member.notes) { mutableStateOf(member.notes ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MemberHeaderSection(
            fullName = "${member.firstName} ${member.lastName}",
            status = status
        )

        Spacer(modifier = Modifier.height(16.dp))

        MemberContactInfo(
            email = member.email,
            phoneNumber = member.phoneNumber
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormulasSection(
            formulas = formulas,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        NotesSection(
            notes = notes,
            onNotesChanged = { updatedNotes ->
                notes = updatedNotes
                onSaveNotes(updatedNotes.trim())
            },
            onClearNotes = {
                notes = ""
                onSaveNotes("")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        AttendanceActionButtons(
            status = status,
            onCollected = onCollected,
            onAbsent = onAbsent,
            onReset = onReset
        )
    }
}

// --- Sub-composables ---

@Composable
private fun MemberHeaderSection(
    fullName: String,
    status: AttendanceStatus
) {
    val containerColor by animateColorAsState(
        targetValue = when (status) {
            AttendanceStatus.COLLECTED -> MaterialTheme.colorScheme.primaryContainer
            AttendanceStatus.ABSENT -> MaterialTheme.colorScheme.errorContainer
            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(durationMillis = 300),
        label = "BadgeColorAnimation"
    )

    val contentColor = when (status) {
        AttendanceStatus.COLLECTED -> MaterialTheme.colorScheme.onPrimaryContainer
        AttendanceStatus.ABSENT -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = fullName,
            style = MaterialTheme.typography.headlineMedium
        )

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = containerColor
        ) {
            Text(
                text = status.toFrench().uppercase(),
                color = contentColor,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun MemberContactInfo(
    email: String,
    phoneNumber: String?
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = MailIcon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        phoneNumber?.let { phone ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = PhoneIcon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = phone,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun FormulasSection(
    formulas: List<WeeklyBasketItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Formules souscrites",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(formulas) { formula ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${formula.category} : ${formula.formula}",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun NotesSection(
    notes: String,
    onNotesChanged: (String) -> Unit,
    onClearNotes: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notes (dettes, remarques...)",
                style = MaterialTheme.typography.titleSmall
            )
            TextButton(onClick = onClearNotes) {
                Text("Effacer")
            }
        }

        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChanged,
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )
    }
}

@Composable
private fun AttendanceActionButtons(
    status: AttendanceStatus,
    onCollected: () -> Unit,
    onAbsent: () -> Unit,
    onReset: () -> Unit
) {
    AnimatedContent(
        targetState = status,
        label = "AttendanceControls"
    ) { currentStatus ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (currentStatus == AttendanceStatus.EXPECTED) {
                Button(
                    onClick = onCollected,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(CheckIcon, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Récupéré")
                }

                OutlinedButton(
                    onClick = onAbsent,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(CloseIcon, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Absent")
                }
            } else {
                Button(
                    onClick = onReset,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(RefreshIcon, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Annuler")
                }
            }
        }
    }
}

// --- Previews ---

@Preview
@Composable
private fun MemberDetailsScreenLightPreview(
    @PreviewParameter(MemberPreviewParameterProvider::class) member: Member
) {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MemberDetailsScreen(
                member = member,
                formulas = PreviewFormulas,
                status = AttendanceStatus.EXPECTED,
            )
        }
    }
}

@Preview
@Composable
private fun MemberDetailsScreenDarkPreview(
    @PreviewParameter(MemberPreviewParameterProvider::class) member: Member
) {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MemberDetailsScreen(
                member = member,
                formulas = PreviewFormulas,
                status = AttendanceStatus.EXPECTED,
            )
        }
    }
}


private val PreviewFormulas = listOf(
    WeeklyBasketItem(formula = "Mini", category = "Légumes"),
    WeeklyBasketItem(formula = "Solo", category = "Fruits")
)
