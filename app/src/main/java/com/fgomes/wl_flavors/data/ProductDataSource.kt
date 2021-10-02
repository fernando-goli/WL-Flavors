package com.fgomes.wl_flavors.data

import android.net.Uri
import com.fgomes.wl_flavors.domain.model.Product

interface ProductDataSource {

    suspend fun getProducts(): List<Product>

    suspend fun uploadProductImage(imageUri: Uri): String

    suspend fun createProduct(product: Product): Product
}