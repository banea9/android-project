package com.example.etherealartefacts.ui.home

import androidx.compose.runtime.mutableStateListOf
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

    private val defRange = 35f..150f
    private val filterRange = 0f..200f

    private val _options = mutableStateListOf(
        CategoryOption(name = "All Categories", isChecked = true),
        CategoryOption(name = "Books", isChecked = false),
        CategoryOption(name = "Gemstones", isChecked = false),
        CategoryOption(name = "Home", isChecked = false),
        CategoryOption(name = "Jewellery", isChecked = false)
    )

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

    private val _defaultRange = MutableStateFlow(defRange)
    private val defaultRange: StateFlow<ClosedFloatingPointRange<Float>> = _defaultRange

    private val _filteredRange = MutableStateFlow(filterRange)
    private val filteredRange: StateFlow<ClosedFloatingPointRange<Float>> = _filteredRange

    private var _filterCount = MutableStateFlow(0)
    var filterCount: StateFlow<Int> = _filterCount

    private var areCategoriesUpdated : Boolean = false
    private var isRangeChanged : Boolean = false
    private var isRatingChanged : Boolean = false


    val options: List<CategoryOption>
        get() = _options

    fun updateOption(index: Int, isChecked: Boolean) {
        _filterCount.value += 1

        if (areCategoriesUpdated) _filterCount.value -= 1

        areCategoriesUpdated = true
        val updatedOption = _options[index].copy(isChecked = isChecked)
        _options[index] = updatedOption

        if (index != 0 && isChecked) {
            val updatedOption = _options[0].copy(isChecked = false)
            _options[0] = updatedOption

        }

        if ((_options[0].isChecked && (!_options[1].isChecked && !_options[2].isChecked && !_options[3].isChecked && !_options[4].isChecked))) {

            areCategoriesUpdated = false
            _filterCount.value -= 1
        }

    }

    fun resetFilter() {
        _filterStarRating.value = 0
        _defaultRange.value = defRange
        _options.forEachIndexed { index, _ ->
            val updatedOption = _options[index].copy(isChecked = index == 0)
            _options[index] = updatedOption
        }
        _filterCount.value = 0
    }

    // Custom getter
    val displayedProducts: StateFlow<List<ProductDetailsModel>> = combine(
        filterCriteria,
        products,
        filteredProducts
    ) { criteria, allProducts, filtered ->
        if (criteria.isNotEmpty() || _filterCount.value > 0) {
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
        if (filtered != filterRange) {
            filtered
        } else {
            default
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), filterRange)


    fun onRatingChange(starRating: Int) {
        _filterCount.value += 1
        if (isRatingChanged) _filterCount.value -= 1
        isRatingChanged = true
        if (starRating == 4) {
            isRatingChanged = false
            _filterCount.value -= 1
        }
        _filterStarRating.value = starRating
    }

    fun onRangeChange(range: ClosedFloatingPointRange<Float>) {
        _filterCount.value += 1

        if (isRangeChanged) _filterCount.value -= 1
        isRangeChanged = true

        if (range.start.roundToInt() == 35 && range.endInclusive.roundToInt() == 150) {
            isRangeChanged = false
            _filterCount.value -= 1
        }
        _filteredRange.value =
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

    fun filterProducts() {
        val filteredList = products.value.filter { product: ProductDetailsModel ->
            val matchTitle = product.title.contains(_filterCriteria.value, ignoreCase = true)

            val matchRating =
                product.rating == if (isRatingChanged) _filterStarRating.value else _defaultStarRating.value
            val category = _options.find { o -> o.name == product.category }

            val matchCategory = areCategoriesUpdated && category?.isChecked == true



            val startPrice: Boolean = if (isRangeChanged) {
                product.price.toFloat() >= _defaultRange.value.start
            } else {
                product.price.toFloat() >= _filteredRange.value.start
            }

            val endPrice: Boolean = if (isRangeChanged) {
                product.price.toFloat() <= _defaultRange.value.endInclusive
            } else {
                product.price.toFloat() <= _filteredRange.value.endInclusive
            }

            matchTitle && matchRating && startPrice && endPrice && matchCategory
        }
        _filteredProducts.value = filteredList
    }

    init {
        getProducts()
    }

}