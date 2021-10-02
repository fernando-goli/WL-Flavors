package com.fgomes.wl_flavors.domain.usecase

import android.net.Uri
import com.fgomes.wl_flavors.domain.model.Product

interface CreateProductUseCase {

    suspend operator fun invoke(
        description: String,
        price: Double,
        imageUri: Uri
    ): Product

}