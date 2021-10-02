package com.fgomes.wl_flavors.domain.usecase

import com.fgomes.wl_flavors.data.ProductRepository
import com.fgomes.wl_flavors.domain.model.Product

class GetProductsUseCaseImpl(
    private val productRepository: ProductRepository
) : GetProductsUseCase {

    override suspend fun invoke(): List<Product> {
        return productRepository.getProducts()
    }
}