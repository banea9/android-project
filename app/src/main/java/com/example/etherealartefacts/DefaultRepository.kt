package com.example.etherealartefacts

import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse
import com.example.etherealartefacts.networking.API

class DefaultRepository(private val apiService: API): ApiHelper {
    override suspend fun login(request: LoginRequest) : LoginResponse {
        return apiService.login(request)
    }
}