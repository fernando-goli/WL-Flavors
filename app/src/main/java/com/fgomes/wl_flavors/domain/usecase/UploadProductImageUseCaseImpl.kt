package com.fgomes.wl_flavors.domain.usecase

import android.net.Uri
import com.fgomes.wl_flavors.data.ProductRepository

class UploadProductImageUseCaseImpl(
    private val productRepository: ProductRepository
): UploadProductImageUseCase {

    override suspend fun invoke(imageUri: Uri): String {
        return productRepository.uploadProductImage(imageUri)
    }
}