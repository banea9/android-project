package com.example.etherealartefacts.models

data class ProductDetailsModel(
    val id: Int,
    val title: String,
    val description: String,
    val shortDescription: String,
    val stock: Int,
    val price: Int,
    val rating: Int,
    val image: String,
    val category: String,
)