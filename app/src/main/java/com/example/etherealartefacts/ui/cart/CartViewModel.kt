package com.example.etherealartefacts.ui.cart

import androidx.lifecycle.ViewModel
import com.example.etherealartefacts.models.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addCartItem(cartItem: CartItem) {
        val itemIndex = _cartItems.value.indexOfFirst { item -> item.name == cartItem.name }

        if (itemIndex != -1) {
            _cartItems.value[itemIndex].quantity++
        } else {
            _cartItems.value = _cartItems.value.toMutableList().apply {
                add(CartItem(
                    name = cartItem.name,
                    price = cartItem.price,
                    image = cartItem.image,
                    quantity = 1
                ))
            }
        }
    }

    fun removeItem(name: String) {
        val updatedList = _cartItems.value.filterNot { item -> item.name == name }
        _cartItems.value = updatedList
    }


}