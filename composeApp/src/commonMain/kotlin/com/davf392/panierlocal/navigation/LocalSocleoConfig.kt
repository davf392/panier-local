package com.davf392.panierlocal.navigation

import androidx.compose.runtime.compositionLocalOf
import com.davf392.panierlocal.core.security.SocleoConfig

val LocalSocleoConfig = compositionLocalOf<SocleoConfig> {
    error("No SocleoConfig provided")
}
