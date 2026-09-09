package com.davf392.panierlocal.core.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.data.DistributionLocation
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistributionLocationHeader(
    currentLocation: DistributionLocation,
    locations: List<DistributionLocation>,
    onLocationSelected: (DistributionLocation) -> Unit,
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

// --- Previews ---

@Preview
@Composable
private fun DistributionLocationHeaderLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                DistributionLocationHeader(
                    currentLocation = previewLocation,
                    locations = previewLocations,
                    onLocationSelected = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun DistributionLocationHeaderDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                DistributionLocationHeader(
                    currentLocation = previewLocation,
                    locations = previewLocations,
                    onLocationSelected = {}
                )
            }
        }
    }
}

private val previewLocation = DistributionLocation("dist-001", "Le Croiseur (Lyon 7)")
private val previewLocations = listOf(
    DistributionLocation("dist-001", "Le Croiseur (Lyon 7)"),
    DistributionLocation("dist-002", "Cabanes (Lyon 8)")
)