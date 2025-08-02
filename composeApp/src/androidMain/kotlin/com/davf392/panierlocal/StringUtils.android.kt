package com.davf392.panierlocal

actual fun formatDecimal(number: Double, decimals: Int): String {
    return "%.${decimals}f".format(number)
}