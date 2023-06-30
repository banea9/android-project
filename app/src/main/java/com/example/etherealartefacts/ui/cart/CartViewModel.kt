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
            val newList = ArrayList(_cartItems.value)
            newList.add(cartItem)

            _cartItems.value = newList
        }
        println("cart: ${_cartItems.value} ${getTotalPrice()}")
    }

    fun removeItem(name: String) {
        val updatedList = _cartItems.value.filterNot { item -> item.name == name }
        _cartItems.value = updatedList
    }

    fun getTotalPrice(): Int {
        return _cartItems.value
            .fold(0) { acc, cartItem ->
                acc + (cartItem.price * cartItem.quantity)
            }
    }


}