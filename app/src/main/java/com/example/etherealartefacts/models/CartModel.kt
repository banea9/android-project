package com.example.etherealartefacts.models


data class CartItem(
    val id: Int,
    val name: String,
    val image: String,
    val price: Int,
    var quantity: Int
)

data class CartOrder(
    val id: Int,
    val cartItems: List<CartItem>,
)
