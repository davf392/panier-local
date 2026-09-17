package com.davf392.panierlocal.data

data class OrderParams(
    val emailAddress: String? = null,
    val clientGroup: String? = null,
    val providerEmailAddress: String? = null,
    val providerGroup: String? = null,
    val collectCode: String? = null,
    val platformCircuit: String? = null,
    val sector: String? = null
)
