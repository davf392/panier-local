package com.davf392.panierlocal.core.security

import com.davf392.panierlocal.data.OrderParams
import org.kotlincrypto.macs.hmac.sha1.HmacSHA1

object SocleoSignature {

    /**
     * Generates a seal for product-related API requests.
     *
     * @param base The base parameters for the request.
     * @param action The specific action to perform.
     * @param apiSecret The secret key used for HMAC-SHA1 signing.
     * @return The HMAC-SHA1 signature as a hexadecimal string.
     */
    fun generateProductsSeal(
        base: BaseParams,
        action: String,
        apiSecret: String
    ): String {
        val dataToSign = "${base.version}*${base.userApi}*${base.date}*$action**${base.adresseEmail}"
        return generateHmacSha1Signature(key = dataToSign, data = apiSecret)
    }

    /**
     * Generates a seal for order-related API requests.
     *
     * @param base The base parameters for the request.
     * @param action The specific action to perform.
     * @param startDate The start date filter.
     * @param endDate The end date filter.
     * @param order The [OrderParams] containing specific filtering criteria.
     * @param apiSecret The secret key used for HMAC-SHA1 signing.
     * @return The HMAC-SHA1 signature as a hexadecimal string.
     */
    fun generateOrdersSeal(
        base: BaseParams,
        action: String,
        startDate: String,
        endDate: String,
        order: OrderParams,
        apiSecret: String
    ): String {

        val dataToSign = listOf(
            base.version,
            base.userApi,
            base.date,
            action,
            startDate,
            endDate,
            order.emailAddress.orEmpty(),
            order.clientGroup.orEmpty(),
            order.providerEmailAddress.orEmpty(),
            order.providerGroup.orEmpty(),
            order.collectCode.orEmpty(),
            order.platformCircuit.orEmpty(),
            order.sector.orEmpty()
        ).joinToString("*")

        return generateHmacSha1Signature(key = dataToSign, data = apiSecret)
    }

    /**
     * Generates a HMAC-SHA1 signature for the given key and data.
     *
     * @param key The data string to sign.
     * @param data The secret key used for signing.
     * @return The HMAC-SHA1 signature as a hexadecimal string.
     */
    fun generateHmacSha1Signature(key: String, data: String): String {
        val secretKeyBytes = data.encodeToByteArray()
        val messageBytes = key.encodeToByteArray()
        
        val hmacBytes = HmacSHA1(secretKeyBytes).doFinal(messageBytes)
        return hmacBytes.toHexString()
    }

    fun ByteArray.toHexString(): String {
        return joinToString("") {
            (it.toInt() and 0xFF).toString(16).padStart(2, '0')
        }
    }
}
