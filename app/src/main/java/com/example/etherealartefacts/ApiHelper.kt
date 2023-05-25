package com.example.etherealartefacts

import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse
import com.example.etherealartefacts.models.ProductDetailsModel

interface ApiHelper {
    suspend fun login(request: LoginRequest): LoginResponse

    suspend fun getProductDetails(id: Int): ProductDetailsModel
}