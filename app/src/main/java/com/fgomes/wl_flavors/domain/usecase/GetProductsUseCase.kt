package com.fgomes.wl_flavors.domain.usecase

import com.fgomes.wl_flavors.domain.model.Product

interface GetProductsUseCase {

    suspend operator fun invoke(): List<Product>

}