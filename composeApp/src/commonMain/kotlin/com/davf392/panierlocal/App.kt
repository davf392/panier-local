package com.davf392.panierlocal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.features.location.LocationViewModel
import com.davf392.panierlocal.navigation.AppNavigation
import com.davf392.panierlocal.navigation.DistributionContext
import com.davf392.panierlocal.navigation.LocalDistributionContext
import com.davf392.panierlocal.repository.MockMemberRepository
import com.davf392.panierlocal.repository.MockProductRepository
import kotlinx.datetime.LocalDateTime

@Composable
fun App() {
    // Shared repositories to maintain state consistency
    val memberRepository = remember { MockMemberRepository() }
    val productRepository = remember { MockProductRepository() }
    
    val locationViewModel: LocationViewModel = viewModel()
    val currentLocation by locationViewModel.currentLocation.collectAsState()

    PanierLocalTheme {
        val distributionContext = remember(currentLocation, locationViewModel.locations) {
            DistributionContext(
                currentLocation = currentLocation,
                locations = locationViewModel.locations,
                onLocationSelected = locationViewModel::setLocation,
                startTime = LocalDateTime(2026, 9, 8, 14, 0),
                endTime = LocalDateTime(2026, 9, 8, 18, 0)
            )
        }

        CompositionLocalProvider(LocalDistributionContext provides distributionContext) {
            AppNavigation()
        }
    }
}
