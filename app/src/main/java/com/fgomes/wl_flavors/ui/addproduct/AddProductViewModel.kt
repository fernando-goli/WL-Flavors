package com.fgomes.wl_flavors.ui.addproduct

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fgomes.wl_flavors.R
import com.fgomes.wl_flavors.domain.model.Product
import com.fgomes.wl_flavors.domain.usecase.CreateProductUseCase
import com.fgomes.wl_flavors.util.fromCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private var isFormValid = false

    private val _imageUriErrorResId = MutableLiveData<Int>()
    val imageUriErrorResId: LiveData<Int> = _imageUriErrorResId

    private val _descriptionFieldErrorResId = MutableLiveData<Int>()
    val descriptionFieldErrorResId: LiveData<Int?> = _descriptionFieldErrorResId

    private val _priceFieldErrorResId = MutableLiveData<Int>()
    val priceFieldErrorResId: LiveData<Int?> = _priceFieldErrorResId

    private val _productCreated = MutableLiveData<Product>()
    val productCreated: LiveData<Product> = _productCreated

    fun createProduct(description: String, price: String, imageUri: Uri?) = viewModelScope.launch {
        isFormValid = true

        _imageUriErrorResId.value = getDrawableResIdIfNull(imageUri)
        _descriptionFieldErrorResId.value = getErrorStringResIdIfEmpty(description)
        _priceFieldErrorResId.value = getErrorStringResIdIfEmpty(price)

        if(isFormValid){
            try{
                val product = createProductUseCase(description, price.fromCurrency(), imageUri!!)
                _productCreated.value = product
            } catch (e: Exception){
                Log.d("CreateProduct", e.toString())
            }
        }

    }

    private fun getErrorStringResIdIfEmpty(value: String): Int?{
        return if (value.isEmpty()){
            isFormValid = false
            R.string.add_product_field_error
        } else null
    }

    private fun getDrawableResIdIfNull(value: Uri?): Int{
        return if (value == null) {
            isFormValid = false
            R.drawable.background_product_image_error
        } else R.drawable.background_product_image
    }
}