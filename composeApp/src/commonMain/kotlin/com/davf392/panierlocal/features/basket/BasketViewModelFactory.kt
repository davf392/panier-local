package com.davf392.panierlocal.features.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.davf392.panierlocal.repository.MockProductRepository
import com.davf392.panierlocal.features.basket.BasketViewModel
import kotlin.reflect.KClass

class BasketViewModelFactory(
    private val productRepository: MockProductRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T = when (modelClass) {
        StaffBasketViewModel::class -> StaffBasketViewModel(repository = productRepository)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
}
