package com.example.etherealartefacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.ProductDetailsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    private val _response = MutableStateFlow<Result<ProductDetailsModel>?>(null)
    val response : StateFlow<Result<ProductDetailsModel>?> = _response

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getProductDetails(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val getProductDetailsResponse = repository.getProductDetails(id)
                _response.value = Result.success(getProductDetailsResponse)
            } catch (e: Exception) {
                _response.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}