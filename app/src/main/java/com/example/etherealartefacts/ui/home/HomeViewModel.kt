package com.example.etherealartefacts.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.ProductDetailsModel
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultRepository,
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductDetailsModel>>(emptyList())
    val products: StateFlow<List<ProductDetailsModel>> = _products

    private val _filteredProducts = MutableStateFlow<List<ProductDetailsModel>>(emptyList())
    val filteredProducts: StateFlow<List<ProductDetailsModel>> = _filteredProducts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorOccurred = MutableStateFlow<Boolean?>(null)
    val errorOccurred: StateFlow<Boolean?> = _errorOccurred

    private val _filterCriteria = MutableStateFlow<String>("")
    val filterCriteria: StateFlow<String> = _filterCriteria

    fun onChange(value: String) {
        _filterCriteria.value = value
        val filteredList = products.value.filter { product: ProductDetailsModel ->
            product.title.contains(value, ignoreCase = true)
        }
        _filteredProducts.value = filteredList
    }

    fun clear() {
        _filterCriteria.value = ""
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val getProductsResponse = repository.getProducts()
                _products.value = getProductsResponse
                _errorOccurred.value = false
            } catch (e: Exception) {
                _errorOccurred.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    init {
        getProducts()
    }

}