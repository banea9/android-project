package com.example.etherealartefacts.networking

import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse
import com.example.etherealartefacts.models.ProductDetailsModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface API {
    @GET(value="products/{id}?populate=*")
    suspend fun getProductDetails(@Path("id") id: Int): ProductDetailsModel

    @POST(value="auth/local")
    suspend fun login(@Body request: LoginRequest): LoginResponse

}