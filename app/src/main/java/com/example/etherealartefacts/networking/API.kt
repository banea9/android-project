package com.example.etherealartefacts.networking

import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface API {
    @POST(value="/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}