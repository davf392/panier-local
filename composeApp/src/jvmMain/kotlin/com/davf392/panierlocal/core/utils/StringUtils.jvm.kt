package com.davf392.panierlocal.core.utils

actual fun formatDecimal(number: Double, decimals: Int): String {
    return "%.${decimals}f".format(number)
}