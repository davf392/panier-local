package com.davf392.panierlocal.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CatalogItemMapperTest {

    @Test
    fun `test mapping membership`() {
        val dto = mapOf(
            "reference" to "REF001",
            "designation" to "Adhésion 2026",
            "quantite" to "1"
        )
        val result = CatalogItemMapper.mapToCatalogItem(dto)
        assertTrue(result is Membership)
        assertEquals("Adhésion 2026", result.designation)
    }

    @Test
    fun `test mapping basket item`() {
        val dto = mapOf(
            "reference" to "PANIER01",
            "designation" to "Panier Légumes Solo - Tarif Plein",
            "quantite" to "5",
            "secteur" to "LEG"
        )
        val result = CatalogItemMapper.mapToCatalogItem(dto)
        assertTrue(result is BasketItem)
        assertEquals(BasketSize.SOLO, result.size)
        assertEquals(PriceType.PLEIN, result.priceType)
        assertEquals(Sector.VEGETABLES, result.sector)
    }

    @Test
    fun `test mapping grocery item`() {
        val dto = mapOf(
            "reference" to "MIEL01",
            "designation" to "Miel acacia 250g",
            "quantite" to "2",
            "poids" to "0.25",
            "secteur" to "CDE"
        )
        val result = CatalogItemMapper.mapToCatalogItem(dto)
        assertTrue(result is GroceryItem)
        assertEquals(0.25, result.weight)
    }

    @Test
    fun `test mapping fournisseur`() {
        val dto = mapOf(
            "code" to "UHHQ3HRE",
            "denomination" to "des graines",
            "nom" to "Doe",
            "adresseMail" to "contact@graines.fr",
            "codePostal" to "07100",
            "ville" to "Annonnay",
            "typeAgriculture" to "B"
        )
        val result = CatalogItemMapper.mapToFournisseur(dto)
        assertNotNull(result)
        assertEquals("UHHQ3HRE", result.code)
        assertEquals("des graines", result.denomination)
        assertEquals("Doe", result.nom)
        assertEquals("Annonnay", result.ville)
        assertEquals("B", result.typeAgriculture)
    }

    @Test
    fun `test mapping boutique product with linked fournisseur`() {
        val fournisseur = Fournisseur(
            code = "4DYDQX5V",
            denomination = "Distillerie Botanique",
            ville = "Saint-Martin-en-Haut"
        )
        val fournisseursMap = mapOf(fournisseur.code to fournisseur)

        val productDto = mapOf(
            "reference" to "QRLMFMYV",
            "designation" to "Liqueur d'Hysope - 50cl",
            "fournisseur" to "4DYDQX5V",
            "unitePrix" to "U",
            "prix" to "26",
            "conditionnement" to "50 cl",
            "famille1" to "Liqueur",
            "typeAgriculture" to "B",
            "logos" to "[AB]",
            "disponible" to "1",
            "communeFournisseur" to "Saint-Martin-en-Haut",
            "origine" to "Rhône",
            "complement" to "Digestif traditionnel"
        )

        val result = CatalogItemMapper.mapToBoutiqueProduct(productDto, fournisseursMap)
        assertNotNull(result)
        assertEquals("QRLMFMYV", result.reference)
        assertEquals("Liqueur d'Hysope - 50cl", result.designation)
        assertEquals(26.0, result.prix)
        assertEquals("50 cl", result.conditionnement)
        assertEquals("Liqueur", result.famille)
        assertEquals("Distillerie Botanique", result.fournisseurNom)
        assertTrue(result.isBio)
        assertTrue(result.disponible)
        assertEquals("Saint-Martin-en-Haut", result.communeFournisseur)
    }

    @Test
    fun `test mapping boutique product fallback provider name`() {
        val productDto = mapOf(
            "reference" to "REF123",
            "designation" to "Produit Local",
            "fournisseur" to "UNKNOWN_CODE",
            "prix" to "12.50",
            "communeFournisseur" to "Oullins",
            "disponible" to "0"
        )

        val result = CatalogItemMapper.mapToBoutiqueProduct(productDto, emptyMap())
        assertNotNull(result)
        assertEquals("Oullins", result.fournisseurNom)
        assertEquals(12.5, result.prix)
        assertFalse(result.disponible)
    }

    @Test
    fun `test mapping boutique product provider nom fallback and empty reference`() {
        val fournisseur = Fournisseur(
            code = "PROV1",
            denomination = "",
            nom = "Martin"
        )
        val productDto = mapOf(
            "reference" to "REF999",
            "fournisseur" to "PROV1"
        )
        val result = CatalogItemMapper.mapToBoutiqueProduct(productDto, mapOf(fournisseur.code to fournisseur))
        assertNotNull(result)
        assertEquals("Martin", result.fournisseurNom)
        assertEquals("Autres", result.famille)
        assertEquals(0.0, result.prix)

        // Invalid / blank reference returns null
        val invalidDto = mapOf("reference" to "   ")
        val invalidResult = CatalogItemMapper.mapToBoutiqueProduct(invalidDto)
        assertEquals(null, invalidResult)
    }
}
