package com.fgomes.wl_flavors.ui.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fgomes.wl_flavors.config.Config
import com.fgomes.wl_flavors.domain.model.Product
import com.fgomes.wl_flavors.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    config: Config
) : ViewModel() {

    private val _productsData = MutableLiveData<List<Product>>()
    val productsData: LiveData<List<Product>> = _productsData

    private val _addButtonVisibilityData = MutableLiveData(config.addButtonVisibility)
    val addButtonVisibilityData : LiveData<Int> = _addButtonVisibilityData

    fun getProducts() = viewModelScope.launch {
        try {
            val products = getProductsUseCase()
            _productsData.value = products
        }catch (e: Exception){
            Log.d("ProductsViewModel", e.toString())
        }
    }
}