package com.example.etherealartefacts.ui.productsDetails

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
class ProductDetailsViewModel @Inject constructor(
    private val repository: DefaultRepository,

    ) : ViewModel() {

    private val _products = MutableStateFlow<ProductDetailsModel?>(null)
    val products: StateFlow<ProductDetailsModel?> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorOccurred = MutableStateFlow<Boolean?>(null)
    val errorOccurred: StateFlow<Boolean?> = _errorOccurred

    fun getProductDetails(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val getProductDetailsResponse = repository.getProductDetails(id)
                _products.value = getProductDetailsResponse
                _errorOccurred.value = false
            } catch (e: Exception) {
                _errorOccurred.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}