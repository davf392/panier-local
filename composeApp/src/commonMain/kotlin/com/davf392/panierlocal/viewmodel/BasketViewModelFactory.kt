package com.davf392.panierlocal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.davf392.panierlocal.repository.ProductRepository
import com.davf392.panierlocal.usecase.CalculateExchangeUseCase
import com.davf392.panierlocal.viewmodel.staff_basket.StaffBasketViewModel
import kotlin.reflect.KClass

class BasketViewModelFactory(
    private val productRepository: ProductRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T = when (modelClass) {
        StaffBasketViewModel::class -> StaffBasketViewModel(repository = productRepository)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
}
