package com.davf392.panierlocal.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.davf392.panierlocal.core.designsystem.BasketIcon
import com.davf392.panierlocal.core.designsystem.DashboardIcon
import com.davf392.panierlocal.core.designsystem.PersonIcon
import org.jetbrains.compose.resources.stringResource
import panierlocal.composeapp.generated.resources.Res
import panierlocal.composeapp.generated.resources.baskets
import panierlocal.composeapp.generated.resources.dashboard
import panierlocal.composeapp.generated.resources.members

@Composable
actual fun AppBottomBar(
    modifier: Modifier
) {
    val navigator = LocalNavigator.currentOrThrow
    val items = listOf(
        BottomNavItem(DashboardScreenVoyager, stringResource(Res.string.dashboard), DashboardIcon),
        BottomNavItem(WeeklyBasketScreenVoyager, stringResource(Res.string.baskets), BasketIcon),
        BottomNavItem(MembersScreenVoyager, stringResource(Res.string.members), PersonIcon),
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = navigator.lastItem == item.screen,
                onClick = { navigator.replaceAll(item.screen) }
            )
        }
    }
}
