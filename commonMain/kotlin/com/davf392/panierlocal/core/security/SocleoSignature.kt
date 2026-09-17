package com.davf392.panierlocal.core.security

import org.kotlincrypto.macs.hmac.sha1.HmacSHA1

object SocleoSignature {

    /**
     * Generates a HMAC-SHA1 signature for the given data and key.
     */
    fun generateHmacSha1Signature(key: String, data: String): String {
        val keyBytes = key.encodeToByteArray()
        val dataBytes = data.encodeToByteArray()
        
        val hmac = HmacSHA1(keyBytes)
        val hmacBytes = hmac.doFinal(dataBytes)
        
        return hmacBytes.toHexString()
    }

    /**
     * Generates the seal for order commands.
     * Uses a multi-line format for clarity.
     */
    fun generateOrderSeal(secretKey: String, timestamp: String, orderId: String): String {
        // Construct the data string on multiple lines for better readability
        val dataToSign = """
            $timestamp
            $orderId
        """.trimIndent()
        
        return generateHmacSha1Signature(secretKey, dataToSign)
    }
}

/**
 * Extension function to convert ByteArray to a Hex String (Multiplatform compatible).
 */
fun ByteArray.toHexString(): String {
    return joinToString("") { (it.toInt() and 0xFF).toString(16).padStart(2, '0') }
}
