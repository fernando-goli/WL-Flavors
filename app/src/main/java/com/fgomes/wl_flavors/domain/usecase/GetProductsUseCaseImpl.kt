package com.fgomes.wl_flavors.domain.usecase

import com.fgomes.wl_flavors.data.ProductRepository
import com.fgomes.wl_flavors.domain.model.Product
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetProductsUseCase {

    override suspend fun invoke(): List<Product> {
        return productRepository.getProducts()
    }
}