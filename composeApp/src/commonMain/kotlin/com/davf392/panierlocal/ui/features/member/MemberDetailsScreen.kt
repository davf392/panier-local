package com.davf392.panierlocal.ui.features.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun MemberDetailsScreen(
    member: Member,
    onSaveNotes: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var notes by remember(member.notes) { mutableStateOf(member.notes ?: "") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "${member.firstName} ${member.lastName}",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Email: ${member.email}",
            style = MaterialTheme.typography.bodyMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Formules (à implémenter)",
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { 
                notes = it
                onSaveNotes(it)
            },
            label = { Text("Notes (dettes, remarques...)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
    }
}

@Preview
@Composable
private fun MemberDetailsScreenPreview(
    @PreviewParameter(MemberPreviewParameterProvider::class) member: Member
) {
    PanierLocalTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MemberDetailsScreen(
                member = member,
                onSaveNotes = {}
            )
        }
    }
}