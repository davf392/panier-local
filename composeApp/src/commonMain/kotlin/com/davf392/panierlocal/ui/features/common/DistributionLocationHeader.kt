package com.davf392.panierlocal.ui.features.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.ui.ArrowDropDownIcon
import com.davf392.panierlocal.ui.LocationOnIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.viewmodel.location.Location
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistributionLocationHeader(
    currentLocation: Location,
    locations: List<Location>,
    onLocationSelected: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Icon(
                    imageVector = LocationOnIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = currentLocation.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            locations.forEach { loc ->
                DropdownMenuItem(
                    text = { Text(loc.name) },
                    onClick = {
                        onLocationSelected(loc)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview
@Composable
private fun DistributionLocationHeaderPreview() {
    PanierLocalTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                DistributionLocationHeader(
                    currentLocation = Location("dist-001", "Le Croiseur (Lyon 7)"),
                    locations = listOf(
                        Location("dist-001", "Le Croiseur (Lyon 7)"),
                        Location("dist-002", "Cabanes (Lyon 8)")
                    ),
                    onLocationSelected = {}
                )
            }
        }
    }
}