package com.example.etherealartefacts

import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse

interface ApiHelper {
    suspend fun login(request: LoginRequest): LoginResponse
}