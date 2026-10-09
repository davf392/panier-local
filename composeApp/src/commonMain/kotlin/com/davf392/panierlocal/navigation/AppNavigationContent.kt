package com.davf392.panierlocal.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScreenTransition

@Composable
internal fun AppNavigationContent(
    modifier: Modifier = Modifier
) {
    Navigator(screen = DashboardScreenVoyager) { navigator ->
        val currentScreen = navigator.lastItem
        val isRootTab = currentScreen is DashboardScreenVoyager ||
                currentScreen is WeeklyBasketScreenVoyager ||
                currentScreen is MembersScreenVoyager

        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = {
                if (isRootTab) {
                    AppBottomBar()
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ScreenTransition(
                    navigator = navigator,
                    transition = {
                        if (navigator.lastEvent == StackEvent.Replace) {
                            fadeIn(animationSpec = tween(150)) togetherWith fadeOut(animationSpec = tween(150))
                        } else {
                            val isPop = navigator.lastEvent == StackEvent.Pop
                            val xOffset = if (isPop) -1 else 1
                            slideInHorizontally { xOffset * it } togetherWith slideOutHorizontally { -xOffset * it }
                        }
                    }
                )
            }
        }
    }
}
