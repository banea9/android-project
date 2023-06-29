package com.example.etherealartefacts.models


data class CartItem(
    val name: String,
    val image: String,
    val price: Int,
    var quantity: Int
)

data class CartModel(
    val items: List<CartItem>,
)

data class CartOrder(
    val id: Int,
    val cartItems: CartModel,
)
