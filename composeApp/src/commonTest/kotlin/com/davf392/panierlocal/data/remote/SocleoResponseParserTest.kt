package com.davf392.panierlocal.data.remote

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SocleoResponseParserTest {

    @Test
    fun `test parse simple socleo response`() {
        val raw = "CODE_RETOUR=00000&SCEAU=33092783df61f49a31d1e54831eea6aba5023dda"
        val params = SocleoResponseParser.parseResponse(raw)

        assertEquals("00000", params["CODE_RETOUR"])
        assertEquals("33092783df61f49a31d1e54831eea6aba5023dda", params["SCEAU"])
    }

    @Test
    fun `test parse response containing json arrays with ampersands inside strings`() {
        val raw = """CODE_RETOUR=00000&SCEAU=a922825ac09dd6b0675c1f2b14fee42567e209fd&FOURNISSEURS=[{"code":"UHHQ3HRE","denomination":"Graines & Co","urlSite":"https://site.com/index.php?a=1&b=2"}]&PRODUITS=[{"reference":"C4MIFLXQ","designation":"Pain & Cie","prix":"20"}]"""
        val params = SocleoResponseParser.parseResponse(raw)

        assertEquals("00000", params["CODE_RETOUR"])
        assertEquals("a922825ac09dd6b0675c1f2b14fee42567e209fd", params["SCEAU"])
        assertTrue(params.containsKey("FOURNISSEURS"))
        assertTrue(params.containsKey("PRODUITS"))

        val fournisseurs = SocleoResponseParser.parseJsonArrayToMapList(params["FOURNISSEURS"]!!)
        assertEquals(1, fournisseurs.size)
        assertEquals("UHHQ3HRE", fournisseurs[0]["code"])
        assertEquals("Graines & Co", fournisseurs[0]["denomination"])
        assertEquals("https://site.com/index.php?a=1&b=2", fournisseurs[0]["urlSite"])

        val produits = SocleoResponseParser.parseJsonArrayToMapList(params["PRODUITS"]!!)
        assertEquals(1, produits.size)
        assertEquals("C4MIFLXQ", produits[0]["reference"])
        assertEquals("Pain & Cie", produits[0]["designation"])
        assertEquals("20", produits[0]["prix"])
    }

    @Test
    fun `test parse get_commandes format`() {
        val raw = """CODE_RETOUR=00000&SCEAU=33092783df61f49a31d1e54831eea6aba5023dda&COMMANDES=[{"reference":"C4MIFLXQ","designation":"Adhésion 2026","quantite":"16","poids":"0","secteur":"CDE","quantiteClient":""},{"reference":"ED252NR6","designation":"Miel acacia 250g","quantite":"2","poids":"0","secteur":"CDE","quantiteClient":""}]"""
        val params = SocleoResponseParser.parseResponse(raw)

        assertEquals("00000", params["CODE_RETOUR"])
        val commandes = SocleoResponseParser.parseJsonArrayToMapList(params["COMMANDES"]!!)
        assertEquals(2, commandes.size)
        assertEquals("C4MIFLXQ", commandes[0]["reference"])
        assertEquals("Adhésion 2026", commandes[0]["designation"])
        assertEquals("16", commandes[0]["quantite"])
        assertEquals("ED252NR6", commandes[1]["reference"])
        assertEquals("Miel acacia 250g", commandes[1]["designation"])
    }
}
