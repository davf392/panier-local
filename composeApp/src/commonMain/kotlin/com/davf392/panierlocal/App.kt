package com.davf392.panierlocal

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.davf392.panierlocal.core.designsystem.BasketIcon
import com.davf392.panierlocal.core.designsystem.DashboardIcon
import com.davf392.panierlocal.core.designsystem.PanierLocalTopAppBar
import com.davf392.panierlocal.core.designsystem.PersonIcon
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.features.basket.BasketScreen
import com.davf392.panierlocal.features.basket.BasketViewModelFactory
import com.davf392.panierlocal.features.basket.StaffBasketViewModel
import com.davf392.panierlocal.features.exchange.CalculateExchangeUseCase
import com.davf392.panierlocal.features.exchange.ExchangeSimulatorScreen
import com.davf392.panierlocal.features.exchange.ExchangeSimulatorViewModel
import com.davf392.panierlocal.features.location.LocationViewModel
import com.davf392.panierlocal.features.member.MemberDetailsScreen
import com.davf392.panierlocal.features.member.MemberDetailsViewModel
import com.davf392.panierlocal.features.member.MemberTrackingScreen
import com.davf392.panierlocal.features.member.MemberTrackingViewModel
import com.davf392.panierlocal.features.staff_dashboard.DashboardScreen
import com.davf392.panierlocal.features.staff_dashboard.DashboardViewModel
import com.davf392.panierlocal.features.staff_dashboard.DistributionUiState
import com.davf392.panierlocal.navigation.DistributionContext
import com.davf392.panierlocal.navigation.LocalDistributionContext
import com.davf392.panierlocal.navigation.Routes
import com.davf392.panierlocal.repository.MockMemberRepository
import com.davf392.panierlocal.repository.MockProductRepository
import kotlin.reflect.KClass

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun App() {
    // Shared repositories to maintain state consistency
    val memberRepository = remember { MockMemberRepository() }
    val productRepository = remember { MockProductRepository() }
    
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

    val canNavigateBack = currentRoute?.startsWith(Routes.EXCHANGE_SIMULATOR) == true || 
                          currentRoute?.startsWith(Routes.MEMBER_DETAILS) == true

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
                        val viewModel: DashboardViewModel = viewModel()
                        val uiState by viewModel.uiState.collectAsState()
                        DashboardScreen(
                            selectedDistribution = uiState ?: DistributionUiState.empty(),
                            onEvent = viewModel::onEvent
                        )
                    }
                    composable(Routes.WEEKLY_BASKET) {
                        val viewModel: StaffBasketViewModel = viewModel(
                            factory = BasketViewModelFactory(MockProductRepository())
                        )
                        val uiState by viewModel.uiState.collectAsState()
                        BasketScreen(
                            uiState = uiState,
                            onExchangeClicked = { productItem ->
                                navController.navigate("${Routes.EXCHANGE_SIMULATOR}/${productItem.id}")
                            }
                        )
                    }

                    composable(Routes.MEMBERS) {
                        val viewModel: MemberTrackingViewModel = viewModel(
                            key = currentLocation.id,
                            factory = object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                                    return MemberTrackingViewModel(
                                        memberRepository,
                                        currentLocation.id
                                    ) as T
                                }
                            }
                        )
                        val uiState by viewModel.uiState.collectAsState()
                        MemberTrackingScreen(
                            uiState = uiState,
                            onCollected = viewModel::markAsCollected,
                            onAbsent = viewModel::markAsAbsent,
                            onReset = viewModel::resetStatus,
                            onSearchQueryChanged = viewModel::onSearchQueryChanged,
                            onMemberClick = { member -> 
                                navController.navigate("${Routes.MEMBER_DETAILS}/${member.id}")
                            }
                        )
                    }

                    composable(
                        route = "${Routes.MEMBER_DETAILS}/{memberId}",
                        arguments = listOf(navArgument("memberId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val memberId = backStackEntry.savedStateHandle.get<String>("memberId") ?: ""
                        val viewModel: MemberDetailsViewModel = viewModel(
                            factory = object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                                    return MemberDetailsViewModel(
                                        memberRepository,
                                        productRepository,
                                        memberId,
                                        currentLocation.id
                                    ) as T
                                }
                            }
                        )
                        val uiState by viewModel.uiState.collectAsState()
                        if (uiState.isLoading) {
                            Text("Chargement...")
                        } else {
                            uiState.member?.let { member ->
                                MemberDetailsScreen(
                                    member = member,
                                    formulas = uiState.formulas,
                                    status = uiState.attendanceStatus ?: AttendanceStatus.EXPECTED,
                                    onCollected = viewModel::markAsCollected,
                                    onAbsent = viewModel::markAsAbsent,
                                    onReset = viewModel::resetStatus,
                                    onSaveNotes = viewModel::updateNotes
                                )
                            }
                        }
                        }
                    composable(
                        route = "${Routes.EXCHANGE_SIMULATOR}/{itemId}",
                        arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val viewModel: ExchangeSimulatorViewModel = viewModel(
                            factory = object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                                    return ExchangeSimulatorViewModel(
                                        MockProductRepository(),
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
}
