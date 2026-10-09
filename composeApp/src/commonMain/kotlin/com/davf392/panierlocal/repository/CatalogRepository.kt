package com.davf392.panierlocal.repository

import com.davf392.panierlocal.data.BoutiqueProduct
import com.davf392.panierlocal.data.CatalogItem
import com.davf392.panierlocal.data.CatalogItemMapper
import com.davf392.panierlocal.data.remote.SocleoRemoteDataSource
import com.davf392.panierlocal.data.remote.SocleoResponseParser
import kotlinx.serialization.json.Json

class CatalogRepository(private val dataSource: SocleoRemoteDataSource) {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Fetches products available in the AMAP boutique using GET_PRODUITS.
     * The response contains both FOURNISSEURS and PRODUITS parameters.
     */
    suspend fun getBoutiqueProducts(): List<BoutiqueProduct> {
        val rawResponse = dataSource.getProducts()
        val params = SocleoResponseParser.parseResponse(rawResponse)

        val returnCode = params["CODE_RETOUR"]
        if (returnCode != null && returnCode != "00000") {
            val errorMessage = params["MESSAGE_ERREUR"] ?: "Erreur Socleo (Code: $returnCode)"
            throw IllegalStateException(errorMessage)
        }

        val fournisseursJson = params["FOURNISSEURS"].orEmpty()
        val fournisseursList = SocleoResponseParser.parseJsonArrayToMapList(fournisseursJson, json)
            .mapNotNull { CatalogItemMapper.mapToFournisseur(it) }
        val fournisseursMap = fournisseursList.associateBy { it.code }

        val produitsJson = params["PRODUITS"] ?: params["PRODUCTS"].orEmpty()
        val produitsMapList = if (produitsJson.isNotBlank()) {
            SocleoResponseParser.parseJsonArrayToMapList(produitsJson, json)
        } else {
            // Fallback in case rawResponse was already a direct JSON array
            SocleoResponseParser.parseJsonArrayToMapList(rawResponse, json)
        }

        return produitsMapList.mapNotNull { dto ->
            CatalogItemMapper.mapToBoutiqueProduct(dto, fournisseursMap)
        }
    }

    suspend fun getCatalog(startDate: String, endDate: String): List<CatalogItem> {
        val rawResponse = dataSource.getCommandes(startDate = startDate, endDate = endDate)
        val params = SocleoResponseParser.parseResponse(rawResponse)

        val returnCode = params["CODE_RETOUR"]
        if (returnCode != null && returnCode != "00000") {
            val errorMessage = params["MESSAGE_ERREUR"] ?: "Erreur Socleo (Code: $returnCode)"
            throw IllegalStateException(errorMessage)
        }

        val commandesJson = params["COMMANDES"] ?: rawResponse
        val commandesList = SocleoResponseParser.parseJsonArrayToMapList(commandesJson, json)

        return commandesList.mapNotNull { CatalogItemMapper.mapToCatalogItem(it) }
    }
}
