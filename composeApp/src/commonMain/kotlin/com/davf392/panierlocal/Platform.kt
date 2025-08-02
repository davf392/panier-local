package com.davf392.panierlocal

sealed class Platform {
    data object Android : Platform()
    data object Desktop : Platform()
    data object Ios : Platform()
    data object Web : Platform()
}

expect fun getPlatform(): Platform