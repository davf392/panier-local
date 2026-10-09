package com.davf392.panierlocal.data

object CatalogItemMapper {

    fun mapToCatalogItem(dto: Map<String, String>): CatalogItem? {
        val reference = dto.nonBlank("reference") ?: return null
        val designation = dto.trimmed("designation")
        val quantity = dto.parseInt("quantite")
        val weight = dto.parseDouble("poids") ?: 0.0
        val sector = parseSector(dto.trimmed("secteur"))

        return when {
            isMembership(designation) -> {
                Membership(
                    reference = reference,
                    designation = designation,
                    quantity = quantity
                )
            }
            isBasket(designation) -> {
                BasketItem(
                    reference = reference,
                    designation = designation,
                    quantity = quantity,
                    size = parseBasketSize(designation),
                    priceType = parsePriceType(designation),
                    sector = sector
                )
            }
            else -> {
                GroceryItem(
                    reference = reference,
                    designation = designation,
                    quantity = quantity,
                    weight = weight
                )
            }
        }
    }

    fun mapToFournisseur(dto: Map<String, String>): Fournisseur? {
        val code = dto.nonBlank("code") ?: return null
        return Fournisseur(
            code = code,
            denomination = dto.trimmed("denomination"),
            nom = dto.trimmed("nom"),
            adresseMail = dto.trimmed("adresseMail"),
            urlSite = dto.trimmed("urlSite"),
            tel1 = dto.trimmed("tel1"),
            tel2 = dto.trimmed("tel2"),
            fax = dto.trimmed("fax"),
            adresse = dto.trimmed("adresse"),
            codePostal = dto.trimmed("codePostal"),
            ville = dto.trimmed("ville"),
            latitude = dto.trimmed("latitude"),
            longitude = dto.trimmed("longitude"),
            typeFournisseur = dto.trimmed("typeFournisseur"),
            typeAgriculture = dto.trimmed("typeAgriculture"),
            categoriesProduit = dto.trimmed("categoriesProduit"),
            label = dto.trimmed("label"),
            certificat = dto.trimmed("certificat"),
            siret = dto.trimmed("siret")
        )
    }

    fun mapToBoutiqueProduct(
        dto: Map<String, String>,
        fournisseursMap: Map<String, Fournisseur> = emptyMap()
    ): BoutiqueProduct? {
        val reference = dto.nonBlank("reference") ?: return null
        val designation = dto.trimmed("designation")
        val fournisseurCode = dto.trimmed("fournisseur")
        val fournisseur = fournisseursMap[fournisseurCode]

        val fournisseurNom = fournisseur?.denomination?.takeIf { it.isNotBlank() }
            ?: fournisseur?.nom?.takeIf { it.isNotBlank() }
            ?: dto.nonBlank("communeFournisseur")
            ?: dto.nonBlank("origine")
            ?: ""

        val prix = dto.parseDouble("prix") ?: dto.parseDouble("prixUnitaire") ?: 0.0
        val prixAchat = dto.parseDouble("prixAchat") ?: 0.0
        val poidsNet = dto.parseDouble("poidsNet") ?: dto.parseDouble("poids") ?: 0.0
        val delaiCommande = dto.parseInt("delaiCommande")
        val typeAgriculture = dto.trimmed("typeAgriculture")
        val logos = dto.trimmed("logos")
        val disponible = dto.trimmed("disponible") != "0"
        val famille = dto.nonBlank("famille1")
            ?: dto.nonBlank("famille2")
            ?: dto.nonBlank("categorie")
            ?: "Autres"

        val productLogos = parseProductLogos(logos)

        return BoutiqueProduct(
            reference = reference,
            designation = designation,
            quantity = dto.parseInt("quantite"),
            prix = prix,
            prixAchat = prixAchat,
            unitePrix = dto.trimmed("unitePrix").ifEmpty { "U" },
            conditionnement = dto.trimmed("conditionnement"),
            poidsNet = poidsNet,
            delaiCommande = delaiCommande,
            urlPhoto = dto.trimmed("urlPhoto"),
            complement = dto.trimmed("complement"),
            categorie = dto.trimmed("categorie"),
            secteur = parseSector(dto.trimmed("secteur")),
            famille = famille,
            typeAgriculture = typeAgriculture,
            disponible = disponible,
            fournisseurCode = fournisseurCode,
            fournisseurNom = fournisseurNom,
            communeFournisseur = dto.trimmed("communeFournisseur"),
            origine = dto.trimmed("origine"),
            logos = logos,
            note = dto.trimmed("note"),
            productLogos = productLogos
        )
    }

    private val LOGO_TOKEN_REGEX = Regex("\\[([A-Za-z0-9_]+)\\]")

    fun parseProductLogos(logos: String): List<ProductLogo> {
        val results = mutableListOf<ProductLogo>()

        val bracketMatches = LOGO_TOKEN_REGEX.findAll(logos).map { it.groupValues[1] }.toList()
        val tokens = if (bracketMatches.isNotEmpty()) {
            bracketMatches
        } else if (logos.isNotBlank()) {
            logos.split(',', ';', ' ', '|').map { it.trim() }.filter { it.isNotEmpty() }
        } else {
            emptyList()
        }

        for (token in tokens) {
            val logo = ProductLogo.fromCode(token)
            if (logo != null && logo !in results) {
                results.add(logo)
            }
        }
        return results
    }

    private fun isBasket(designation: String): Boolean =
        designation.contains("Panier", ignoreCase = true)

    private fun isMembership(designation: String): Boolean =
        designation.contains("Adhésion", ignoreCase = true)

    fun parseSector(sector: String?): Sector = when (sector?.trim()?.uppercase()) {
        "PAINOEUF" -> Sector.BREAD_AND_EGGS
        "FRT" -> Sector.FRUITS
        "PL" -> Sector.DAIRY_AND_CHEESE
        "LEG" -> Sector.VEGETABLES
        "CDE" -> Sector.GROCERY
        else -> Sector.OTHER
    }

    fun parseBasketSize(designation: String): BasketSize = when {
        designation.contains("Solo", ignoreCase = true) -> BasketSize.SOLO
        designation.contains("Mini", ignoreCase = true) -> BasketSize.MINI
        designation.contains("Tandem", ignoreCase = true) -> BasketSize.TANDEM
        designation.contains("Famille", ignoreCase = true) -> BasketSize.FAMILLE
        else -> BasketSize.OTHER
    }

    fun parsePriceType(designation: String): PriceType =
        if (designation.contains("Réduit", ignoreCase = true)) PriceType.REDUIT else PriceType.PLEIN

    private fun Map<String, String>.trimmed(key: String): String =
        this[key]?.trim().orEmpty()

    private fun Map<String, String>.nonBlank(key: String): String? =
        this[key]?.trim()?.takeIf { it.isNotBlank() }

    private fun Map<String, String>.parseInt(key: String, default: Int = 0): Int =
        this[key]?.trim()?.toIntOrNull() ?: default

    private fun Map<String, String>.parseDouble(key: String): Double? =
        this[key]?.trim()?.replace(',', '.')?.toDoubleOrNull()
}
