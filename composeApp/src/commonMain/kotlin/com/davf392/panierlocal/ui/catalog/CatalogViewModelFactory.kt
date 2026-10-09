package com.davf392.panierlocal.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.davf392.panierlocal.core.security.SocleoConfig
import com.davf392.panierlocal.data.remote.SocleoRemoteDataSource
import com.davf392.panierlocal.repository.CatalogRepository
import kotlin.reflect.KClass

class CatalogViewModelFactory(
    private val socleoConfig: SocleoConfig
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        if (modelClass == CatalogViewModel::class) {
            val dataSource = SocleoRemoteDataSource(config = socleoConfig)
            val repository = CatalogRepository(dataSource = dataSource)
            @Suppress("UNCHECKED_CAST")
            return CatalogViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
