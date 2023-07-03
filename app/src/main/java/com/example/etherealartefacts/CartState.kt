package com.example.etherealartefacts

import androidx.compose.runtime.mutableStateListOf
import com.example.etherealartefacts.models.CartItem
import com.example.etherealartefacts.models.CartOrder

class CartState {
    companion object {
        var cartItems = mutableStateListOf<CartItem>()
        private var orders = mutableStateListOf<CartOrder>()

        fun addProduct(cartItem: CartItem) {
            val itemIndex = cartItems.indexOfFirst { item -> item.id == cartItem.id }
            if (itemIndex != -1) {
                cartItems[itemIndex].quantity++
            } else {
                cartItems.add(cartItem)
            }
        }

        fun removeProduct(id: Int) = cartItems.removeIf { it.id == id }

        fun placeOrder() {
            orders.add(CartOrder(orders.size, cartItems))
            cartItems.clear()
        }

        fun getTotalPrice(): Int {
            return cartItems
                .fold(0) { acc, cartItem ->
                    acc + (cartItem.price * cartItem.quantity)
                }
        }
    }
}