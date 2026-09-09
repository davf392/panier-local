package com.davf392.panierlocal.ui.features.dashboard.components

import androidx.compose.foundation.layout.Arrangement
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
import com.davf392.panierlocal.data.DistributionLocation
import com.davf392.panierlocal.ui.LocationOnIcon
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.ui.utils.formatDateTime
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistributionHeaderCard(
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    currentLocation: DistributionLocation,
    locations: List<DistributionLocation>,
    onLocationSelected: (DistributionLocation) -> Unit,
    modifier: Modifier = Modifier
) {
    // ... (rest of the code unchanged) ...
    val (date, time) = formatDateTime(startTime, endTime)
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top Row: Date and Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Bottom: Location Selector
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = LocationOnIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = currentLocation.name,
                            style = MaterialTheme.typography.bodyMedium,
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
    }
}

// --- Previews ---

@Preview
@Composable
private fun DistributionHeaderCardLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            DistributionHeaderCard(
                startTime = mockStartTime,
                endTime = mockEndTime,
                currentLocation = mockCurrentLocation,
                locations = mockLocations,
                onLocationSelected = {}
            )
        }
    }
}

@Preview
@Composable
private fun DistributionHeaderCardDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            DistributionHeaderCard(
                startTime = mockStartTime,
                endTime = mockEndTime,
                currentLocation = mockCurrentLocation,
                locations = mockLocations,
                onLocationSelected = {}
            )
        }
    }
}


private val mockStartTime = LocalDateTime(2026, 9, 8, 14, 0)
private val mockEndTime = LocalDateTime(2026, 9, 8, 18, 0)
private val mockCurrentLocation = DistributionLocation("dist-001", "Le Croiseur (Lyon 7)")
private val mockLocations = listOf(
    DistributionLocation("dist-001", "Le Croiseur (Lyon 7)"),
    DistributionLocation("dist-002", "Cabanes (Lyon 8)")
)