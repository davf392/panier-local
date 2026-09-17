package com.davf392.panierlocal.data.remote

import com.davf392.panierlocal.core.security.BaseParams
import com.davf392.panierlocal.core.security.SocleoConfig
import com.davf392.panierlocal.core.security.SocleoSignature
import com.davf392.panierlocal.data.OrderParams
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json

/**
 * Data source responsible for communicating with the Socleo API.
 */
class SocleoRemoteDataSource(
    private val httpClient: HttpClient = createDefaultHttpClient(),
    private val config: SocleoConfig
) {
    suspend fun getProducts(): String {
        val action = "GET_PRODUITS"
        val base = getBaseParams()

        val seal = SocleoSignature.generateProductsSeal(
            base = base,
            action = action,
            apiSecret = config.apiSecret
        )

        val response: HttpResponse = httpClient.get(config.baseUrl) {
            parameter("VERSION", base.version)
            parameter("USER_API", base.userApi)
            parameter("DATE", base.date)
            parameter("ACTION", action)
            parameter("ADRESSE_MAIL", base.adresseEmail)
            parameter("SCEAU", seal)

        }
        return response.bodyAsText()
    }

    suspend fun getCommandes(
        action: String = "GET_COMMANDES",
        startDate: String,
        endDate: String,
        order: OrderParams? = null
    ): String {
        val base = getBaseParams()
        val safeOrder = order ?: OrderParams()

        val seal = SocleoSignature.generateOrdersSeal(
            base = base,
            action = action,
            startDate = startDate,
            endDate = endDate,
            order = safeOrder,
            apiSecret = config.apiSecret
        )

        val response: HttpResponse = httpClient.get(config.baseUrl) {
            parameter("USER_API", base.userApi)
            parameter("ADRESSE_EMAIL", base.adresseEmail)
            parameter("VERSION", base.version)
            parameter("DATE", base.date)
            parameter("ACTION", action)
            parameter("DATE1", startDate)
            parameter("DATE2", endDate)
            parameter("GROUPE_CLIENT", safeOrder.emailAddress)
            parameter("ADRESSE_MAIL_FOURNISSEUR", safeOrder.providerEmailAddress)
            parameter("GROUPE_FOURNISSEUR", safeOrder.providerGroup)
            parameter("CODE_COLLECTE", safeOrder.collectCode)
            parameter("CIRCUIT_PLATEFORME", safeOrder.platformCircuit)
            parameter("SECTEUR", safeOrder.sector)
            parameter("SCEAU", seal)
        }
        return response.bodyAsText()
    }

    private fun getBaseParams(): BaseParams {
        return BaseParams(
            userApi = config.userApi,
            adresseEmail = config.userEmail,
            version = config.apiVersion,
            date = getCurrentFormattedDate()
        )
    }

    companion object {
        fun createDefaultHttpClient(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    })
                }
            }
        }

        fun getCurrentFormattedDate(): String {
            val now = Clock.System.now()
            val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
            val day = today.dayOfMonth.toString().padStart(2, '0')
            val month = today.monthNumber.toString().padStart(2, '0')
            val year = today.year.toString()
            return "$day/$month/$year"
        }
    }
}