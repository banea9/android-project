package com.example.etherealartefacts.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.ProductDetailsModel
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.roundToInt

data class CategoryOption(
    val name: String,
    val isChecked: Boolean,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultRepository,
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductDetailsModel>>(emptyList())
    private val products: StateFlow<List<ProductDetailsModel>> = _products

    private val _filteredProducts = MutableStateFlow<List<ProductDetailsModel>>(emptyList())
    private val filteredProducts: StateFlow<List<ProductDetailsModel>> = _filteredProducts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorOccurred = MutableStateFlow<Boolean?>(null)
    val errorOccurred: StateFlow<Boolean?> = _errorOccurred

    private val _filterCriteria = MutableStateFlow("")
    val filterCriteria: StateFlow<String> = _filterCriteria

    private val _defaultStarRating = MutableStateFlow(4)
    private val defaultStarRating: StateFlow<Int> = _defaultStarRating

    private val _filterStarRating = MutableStateFlow(0)
    private val filterStarRating: StateFlow<Int> = _filterStarRating

    private val _defaultRange = MutableStateFlow(35f..150f)
    private val defaultRange: StateFlow<ClosedFloatingPointRange<Float>> = _defaultRange
    private val _filteredRange = MutableStateFlow(0f..200f)
    private val filteredRange: StateFlow<ClosedFloatingPointRange<Float>> = _filteredRange


    // Custom getter
    val displayedProducts: StateFlow<List<ProductDetailsModel>> = combine(
        filterCriteria,
        products,
        filteredProducts
    ) { criteria, allProducts, filtered ->
        if (criteria.isNotEmpty()) {
            filtered
        } else {
            allProducts
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val displayedRating: StateFlow<Int> = combine(
        defaultStarRating, filterStarRating
    ) { default, filtered ->
        if (filtered != 0) {
            filtered
        } else {
            default
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val displayedRange: StateFlow<ClosedFloatingPointRange<Float>> = combine(
        defaultRange, filteredRange
    ) { default, filtered ->
        if (filtered != 0f..200f) {
            filtered
        } else {
            default
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0f..200f)


    fun onRatingChange(starRating: Int) {
        _filterStarRating.value = starRating
    }

    fun onRangeChange(range: ClosedFloatingPointRange<Float>) {
        _defaultRange.value =
            range.start.roundToInt().toFloat()..range.endInclusive.roundToInt().toFloat()
    }

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

    private fun getProducts() {
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