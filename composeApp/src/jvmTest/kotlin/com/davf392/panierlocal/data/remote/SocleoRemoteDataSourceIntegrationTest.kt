package com.davf392.panierlocal.data.remote

import com.davf392.panierlocal.core.security.SocleoConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertTrue

class SocleoRemoteDataSourceIntegrationTest {

    private val customLogger = object : Logger {
        override fun log(message: String) {
            println("HTTP Client: $message")
        }
    }

    @Test
    fun `test real network request`() = runBlocking {
        val httpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
//            install(Logging) {
//                level = LogLevel.ALL
//                logger = customLogger
//            }
        }

        val dataSource = SocleoRemoteDataSource(
            httpClient = httpClient,
            config = SocleoConfig(
                baseUrl = "https://www.adeuxpresdechezvous.fr/api2.stp?",
                userApi = "adeuxpresdechezvous",
                userEmail = "info@adeuxpresdechezvous.fr",
                apiVersion = "2.3.5",
                apiSecret = "<API_KEY>"
            )
        )

        try {
            println("----------------------------------------------------")
            println("Trying to get PRODUCTS...")
            val productsResponse = dataSource.getProducts()

            println("Products response received : $productsResponse")
            assertTrue(productsResponse.isNotBlank(), "Response should not be empty")

            println("----------------------------------------------------")
            println("Trying to get ORDERS...")
            val ordersResponse = dataSource.getCommandes(
                action = "GET_COMMANDES",
                startDate = "01/04/2026",
                endDate = "01/05/2026"
            )

            println("Orders response received : $ordersResponse")
            assertTrue(ordersResponse.isNotBlank(), "Response should not be empty")

        } catch (e: Exception) {
            println("Error during test: ${e.message}")
            e.printStackTrace()
            throw e
        } finally {
            httpClient.close()
        }
    }
}
