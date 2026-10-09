package com.davf392.panierlocal.data

enum class BasketSize { SOLO, MINI, TANDEM, FAMILLE, OTHER }
enum class PriceType { PLEIN, REDUIT }
enum class Sector { BREAD_AND_EGGS, FRUITS, DAIRY_AND_CHEESE, VEGETABLES, GROCERY, OTHER }
enum class ProductLogo(val code: String) {
    AB("AB"),
    NP("NP"),
    EU("EU"),
    AP("AP");

    companion object {
        fun fromCode(code: String): ProductLogo? =
            entries.firstOrNull { it.code.equals(code.trim(), ignoreCase = true) }
    }
}

sealed interface CatalogItem {
    val reference: String
    val designation: String
    val quantity: Int
}

data class BasketItem(
    override val reference: String,
    override val designation: String,
    override val quantity: Int,
    val size: BasketSize,
    val priceType: PriceType,
    val sector: Sector
) : CatalogItem

data class GroceryItem(
    override val reference: String,
    override val designation: String,
    override val quantity: Int,
    val weight: Double
) : CatalogItem

data class Membership(
    override val reference: String,
    override val designation: String,
    override val quantity: Int
) : CatalogItem

data class Fournisseur(
    val code: String,
    val denomination: String = "",
    val nom: String = "",
    val adresseMail: String = "",
    val urlSite: String = "",
    val tel1: String = "",
    val tel2: String = "",
    val fax: String = "",
    val adresse: String = "",
    val codePostal: String = "",
    val ville: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val typeFournisseur: String = "",
    val typeAgriculture: String = "",
    val categoriesProduit: String = "",
    val label: String = "",
    val certificat: String = "",
    val siret: String = ""
)

data class BoutiqueProduct(
    override val reference: String,
    override val designation: String,
    override val quantity: Int = 0,
    val prix: Double = 0.0,
    val prixAchat: Double = 0.0,
    val unitePrix: String = "U",
    val conditionnement: String = "",
    val poidsNet: Double = 0.0,
    val delaiCommande: Int = 0,
    val urlPhoto: String = "",
    val complement: String = "",
    val categorie: String = "",
    val secteur: Sector = Sector.OTHER,
    val famille: String = "",
    val typeAgriculture: String = "",
    val disponible: Boolean = true,
    val fournisseurCode: String = "",
    val fournisseurNom: String = "",
    val communeFournisseur: String = "",
    val origine: String = "",
    val logos: String = "",
    val note: String = "",
    val productLogos: List<ProductLogo> = CatalogItemMapper.parseProductLogos(logos)
) : CatalogItem

