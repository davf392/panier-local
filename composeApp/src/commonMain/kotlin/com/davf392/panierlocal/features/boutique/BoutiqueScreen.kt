package com.davf392.panierlocal.features.boutique

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.core.designsystem.CloseIcon
import com.davf392.panierlocal.core.designsystem.PersonIcon
import com.davf392.panierlocal.core.designsystem.RefreshIcon
import com.davf392.panierlocal.core.designsystem.ShoppingCartIcon
import com.davf392.panierlocal.core.designsystem.WarningIcon
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.core.utils.formatDecimal
import com.davf392.panierlocal.data.BoutiqueProduct
import com.davf392.panierlocal.ui.catalog.CatalogUiState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import panierlocal.composeapp.generated.resources.Res
import panierlocal.composeapp.generated.resources.boutique_badge_bio
import panierlocal.composeapp.generated.resources.boutique_badge_unavailable
import panierlocal.composeapp.generated.resources.boutique_category_all
import panierlocal.composeapp.generated.resources.boutique_category_other
import panierlocal.composeapp.generated.resources.boutique_empty
import panierlocal.composeapp.generated.resources.boutique_error_title
import panierlocal.composeapp.generated.resources.boutique_loading
import panierlocal.composeapp.generated.resources.boutique_no_search_results
import panierlocal.composeapp.generated.resources.boutique_products_count
import panierlocal.composeapp.generated.resources.boutique_search_placeholder
import panierlocal.composeapp.generated.resources.boutique_unit_price
import panierlocal.composeapp.generated.resources.clear
import panierlocal.composeapp.generated.resources.refresh
import panierlocal.composeapp.generated.resources.retry

@Composable
fun BoutiqueScreen(
    uiState: CatalogUiState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (uiState) {
            is CatalogUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Text(
                            text = stringResource(Res.string.boutique_loading),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            is CatalogUiState.Success -> {
                val boutiqueProducts = uiState.boutiqueProducts

                if (boutiqueProducts.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = ShoppingCartIcon,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = stringResource(Res.string.boutique_empty),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Button(onClick = onRetry) {
                                Text(stringResource(Res.string.refresh))
                            }
                        }
                    }
                } else {
                    BoutiqueProductsContent(
                        products = boutiqueProducts,
                        onRefresh = onRetry
                    )
                }
            }

            is CatalogUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = WarningIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = stringResource(Res.string.boutique_error_title),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = uiState.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onRetry) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = RefreshIcon,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(stringResource(Res.string.retry))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoutiqueProductsContent(
    products: List<BoutiqueProduct>,
    onRefresh: () -> Unit
) {
    val allCategoryLabel = stringResource(Res.string.boutique_category_all)
    val otherCategoryLabel = stringResource(Res.string.boutique_category_other)

    var searchQuery by remember { mutableStateOf("") }
    val categories = remember(products, allCategoryLabel, otherCategoryLabel) {
        listOf(allCategoryLabel) + products.map { it.famille.ifBlank { otherCategoryLabel } }.distinct().sorted()
    }
    var selectedCategory by remember { mutableStateOf(allCategoryLabel) }

    val filteredProducts = remember(products, searchQuery, selectedCategory, allCategoryLabel, otherCategoryLabel) {
        products.filter { product ->
            val productCategory = product.famille.ifBlank { otherCategoryLabel }
            val matchesCategory = (selectedCategory == allCategoryLabel) || (productCategory == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                    product.designation.contains(searchQuery, ignoreCase = true) ||
                    product.fournisseurNom.contains(searchQuery, ignoreCase = true) ||
                    product.famille.contains(searchQuery, ignoreCase = true) ||
                    product.communeFournisseur.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text(stringResource(Res.string.boutique_search_placeholder)) },
            leadingIcon = {
                Icon(
                    imageVector = ShoppingCartIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = CloseIcon,
                            contentDescription = stringResource(Res.string.clear),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        // Categories horizontal chips
        if (categories.size > 2) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = (selectedCategory == category),
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        // Count of results
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.boutique_products_count, filteredProducts.size),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            IconButton(onClick = onRefresh, modifier = Modifier.size(28.dp)) {
                Icon(
                    imageVector = RefreshIcon,
                    contentDescription = stringResource(Res.string.refresh),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Products List
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.boutique_no_search_results),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProducts, key = { it.reference }) { product ->
                    BoutiqueProductCard(product = product)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BoutiqueProductCard(
    product: BoutiqueProduct,
    modifier: Modifier = Modifier
) {
    val cardAlpha = if (product.disponible) 1f else 0.6f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(cardAlpha),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Badges row (Category, Bio, Availability)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (product.famille.isNotBlank()) {
                    ProductBadge(
                        text = product.famille,
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                        textColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                if (product.isBio) {
                    ProductBadge(
                        text = stringResource(Res.string.boutique_badge_bio),
                        backgroundColor = Color(0xFFC8E6C9),
                        textColor = Color(0xFF1B5E20)
                    )
                }

                if (!product.disponible) {
                    ProductBadge(
                        text = stringResource(Res.string.boutique_badge_unavailable),
                        backgroundColor = MaterialTheme.colorScheme.errorContainer,
                        textColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title & Price Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    Text(
                        text = product.designation,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )


                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${formatDecimal(product.prix, 2)} €",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (product.unitePrix.isNotBlank() && product.unitePrix != "U") {
                        Text(
                            text = stringResource(Res.string.boutique_unit_price, product.unitePrix),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Provider & Location
            if (product.fournisseurNom.isNotBlank() || product.communeFournisseur.isNotBlank() || product.origine.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = PersonIcon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    val providerText = buildString {
                        if (product.fournisseurNom.isNotBlank()) {
                            append(product.fournisseurNom)
                        }
                        val loc = product.communeFournisseur.ifBlank { product.origine }
                        if (loc.isNotBlank()) {
                            if (isNotEmpty()) append(" • ")
                            append(loc)
                        }
                    }
                    Text(
                        text = providerText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

@Preview
@Composable
fun BoutiqueScreenPreview() {
    val mockProducts = listOf(
        BoutiqueProduct(
            reference = "C4MIFLXQ",
            designation = "Adhésion 2026",
            prix = 20.0,
            unitePrix = "U",
            famille = "Adhésion",
            fournisseurNom = "AMAP Locale",
            communeFournisseur = "Oullins",
            complement = "Adhésion annuelle à l'association pour la saison 2026."
        ),
        BoutiqueProduct(
            reference = "QRLMFMYV",
            designation = "Liqueur d'Hysope - 50cl",
            prix = 26.0,
            unitePrix = "U",
            conditionnement = "50 cl",
            famille = "Liqueur",
            fournisseurNom = "Distillerie du Coin",
            communeFournisseur = "Saint-Martin-en-Haut",
            origine = "Rhône",
            isBio = true,
            complement = "Plante aromatique distillée avec soin."
        ),
        BoutiqueProduct(
            reference = "ED252NR6",
            designation = "Miel d'Acacia 250g",
            prix = 5.5,
            unitePrix = "U",
            conditionnement = "250g",
            famille = "Épicerie",
            fournisseurNom = "Les Ruches de la Vallée",
            communeFournisseur = "Annonnay",
            isBio = true,
            disponible = true
        )
    )

    PanierLocalTheme(useDarkTheme = false) {
        BoutiqueScreen(uiState = CatalogUiState.Success(mockProducts))
    }
}
