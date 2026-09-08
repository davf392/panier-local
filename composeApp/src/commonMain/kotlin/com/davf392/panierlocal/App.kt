package com.davf392.panierlocal

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.davf392.panierlocal.repository.MockMemberRepository
import com.davf392.panierlocal.repository.ProductRepository
import com.davf392.panierlocal.ui.*
import com.davf392.panierlocal.ui.features.common.PanierLocalTopAppBar
import com.davf392.panierlocal.ui.features.dashboard.DashboardScreen
import com.davf392.panierlocal.ui.features.member.MemberTrackingScreen
import com.davf392.panierlocal.ui.features.screens.BasketScreen
import com.davf392.panierlocal.ui.features.screens.ExchangeSimulatorScreen
import com.davf392.panierlocal.ui.navigation.Routes
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.usecase.CalculateExchangeUseCase
import com.davf392.panierlocal.viewmodel.BasketViewModelFactory
import com.davf392.panierlocal.viewmodel.exchange.ExchangeSimulatorViewModel
import com.davf392.panierlocal.viewmodel.location.LocationViewModel
import com.davf392.panierlocal.viewmodel.member.MemberTrackingViewModel
import com.davf392.panierlocal.viewmodel.staff_basket.StaffBasketViewModel
import kotlin.reflect.KClass

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun App() {
    val navController = rememberNavController()
    val locationViewModel: LocationViewModel = viewModel()
    val currentLocation by locationViewModel.currentLocation.collectAsState()
    val items = listOf(
        BottomNavItem(Routes.DASHBOARD, "Dashboard", DashboardIcon),
        BottomNavItem(Routes.WEEKLY_BASKET, "Paniers", BasketIcon),
        BottomNavItem(Routes.MEMBERS, "Adhérents", PersonIcon),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentTitle = when {
        currentRoute == Routes.DASHBOARD -> "Dashboard"
        currentRoute == Routes.WEEKLY_BASKET -> "Paniers"
        currentRoute == Routes.MEMBERS -> "Adhérents"
        currentRoute?.startsWith(Routes.EXCHANGE_SIMULATOR) == true -> "Simulateur"
        else -> "AMAP"
    }

    val canNavigateBack = currentRoute?.startsWith(Routes.EXCHANGE_SIMULATOR) == true

    PanierLocalTheme {
        Scaffold(
            topBar = {
                PanierLocalTopAppBar(
                    title = currentTitle,
                    onBackClicked = if (canNavigateBack) { { navController.popBackStack() } } else null
                )
            },
            bottomBar = {
                NavigationBar {
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.DASHBOARD,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Routes.DASHBOARD) {
                    DashboardScreen(
                        currentLocation = currentLocation,
                        locations = locationViewModel.locations,
                        onLocationSelected = locationViewModel::setLocation
                    )
                }
                composable(Routes.WEEKLY_BASKET) {
                    val viewModel: StaffBasketViewModel = viewModel(
                        factory = BasketViewModelFactory(ProductRepository())
                    )
                    val uiState by viewModel.uiState.collectAsState()
                    BasketScreen(
                        uiState = uiState,
                        currentLocation = currentLocation,
                        locations = locationViewModel.locations,
                        onLocationSelected = locationViewModel::setLocation,
                        onExchangeClicked = { productItem ->
                            navController.navigate("${Routes.EXCHANGE_SIMULATOR}/${productItem.id}")
                        }
                    )
                }

                composable(Routes.MEMBERS) {
                    val viewModel: MemberTrackingViewModel = viewModel(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                                return MemberTrackingViewModel(
                                    MockMemberRepository(),
                                    currentLocation.id
                                ) as T
                            }
                        }
                    )
                    MemberTrackingScreen(viewModel = viewModel)
                }

                composable(
                    route = "${Routes.EXCHANGE_SIMULATOR}/{itemId}",
                    arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val viewModel: ExchangeSimulatorViewModel = viewModel(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                                return ExchangeSimulatorViewModel(
                                    ProductRepository(),
                                    CalculateExchangeUseCase()
                                ) as T
                            }
                        }
                    )

                    val itemId = backStackEntry.savedStateHandle.get<String>("itemId")
                    LaunchedEffect(itemId) {
                        viewModel.navigateToExchangeSimulatorScreen(itemId)
                    }

                    val exchangeUiState by viewModel.exchangeUiState.collectAsState()
                    exchangeUiState?.let { uiState ->
                        ExchangeSimulatorScreen(
                            uiState = uiState,
                            onProductSelected = { product -> viewModel.selectProduct(product) }
                        )
                    }
                }
            }
        }
    }
}
