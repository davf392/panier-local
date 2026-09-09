package com.davf392.panierlocal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.davf392.panierlocal.core.designsystem.PanierLocalTopAppBar
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.features.location.LocationViewModel
import com.davf392.panierlocal.navigation.AppBottomBar
import com.davf392.panierlocal.navigation.DashboardScreenVoyager
import com.davf392.panierlocal.navigation.DistributionContext
import com.davf392.panierlocal.navigation.LocalDistributionContext
import com.davf392.panierlocal.navigation.Routes
import com.davf392.panierlocal.repository.MockMemberRepository
import com.davf392.panierlocal.repository.MockProductRepository
import org.jetbrains.compose.resources.stringResource
import panierlocal.composeapp.generated.resources.Res
import panierlocal.composeapp.generated.resources.amap
import panierlocal.composeapp.generated.resources.baskets
import panierlocal.composeapp.generated.resources.dashboard
import panierlocal.composeapp.generated.resources.members
import panierlocal.composeapp.generated.resources.simulator

@Composable
fun App() {
    // Shared repositories to maintain state consistency
    val memberRepository = remember { MockMemberRepository() }
    val productRepository = remember { MockProductRepository() }
    
    val navController = rememberNavController()
    val locationViewModel: LocationViewModel = viewModel()
    val currentLocation by locationViewModel.currentLocation.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentTitle = when {
        currentRoute == Routes.DASHBOARD -> stringResource(Res.string.dashboard)
        currentRoute == Routes.WEEKLY_BASKET -> stringResource(Res.string.baskets)
        currentRoute == Routes.MEMBERS -> stringResource(Res.string.members)
        currentRoute?.startsWith(Routes.EXCHANGE_SIMULATOR) == true -> stringResource(Res.string.simulator)
        else -> stringResource(Res.string.amap)
    }

    PanierLocalTheme {
        val distributionContext = remember(currentLocation, locationViewModel.locations) {
            DistributionContext(
                currentLocation = currentLocation,
                locations = locationViewModel.locations,
                onLocationSelected = locationViewModel::setLocation,
                startTime = kotlinx.datetime.LocalDateTime(2026, 9, 8, 14, 0),
                endTime = kotlinx.datetime.LocalDateTime(2026, 9, 8, 18, 0)
            )
        }

        CompositionLocalProvider(LocalDistributionContext provides distributionContext) {
            Navigator(screen = DashboardScreenVoyager) { navigator ->
                Scaffold(
                    topBar = {
                        PanierLocalTopAppBar(
                            title = currentTitle,
                            onBackClicked = if (navigator.canPop) { { navigator.pop() } } else null
                        )
                    },
                    bottomBar = { AppBottomBar() }
                ) { innerPadding ->
                    SlideTransition(navigator) { screen ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            screen.Content()
                        }
                    }
                }
            }
        }
    }
}
