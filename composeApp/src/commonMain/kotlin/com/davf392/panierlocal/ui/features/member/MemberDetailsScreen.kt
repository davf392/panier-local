package com.davf392.panierlocal.ui.features.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.ui.MailIcon
import com.davf392.panierlocal.ui.PhoneIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun MemberDetailsScreen(
    member: Member,
    formulas: List<WeeklyBasketItem>,
    onSaveNotes: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var notes by remember(member.notes) { mutableStateOf(member.notes ?: "") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "${member.firstName} ${member.lastName}",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(MailIcon, contentDescription = null, modifier = Modifier.size(20.dp))
            Text(
                text = member.email,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        member.phoneNumber?.let { phone ->
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(PhoneIcon, contentDescription = null, modifier = Modifier.size(20.dp))
                Text(
                    text = phone,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Formules souscrites",
            style = MaterialTheme.typography.titleSmall
        )
        
        LazyColumn(modifier = Modifier.height(150.dp)) {
            items(formulas) { formula ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(
                        text = "${formula.category} : ${formula.formula}",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

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
                formulas = listOf(
                    WeeklyBasketItem(formula = "Mini", category = "Légumes"),
                    WeeklyBasketItem(formula = "Solo", category = "Fruits")
                ),
                onSaveNotes = {}
            )
        }
    }
}