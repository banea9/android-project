package com.example.etherealartefacts

import androidx.lifecycle.ViewModel
import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {
    suspend fun login(request: LoginRequest): LoginResponse {
        return repository.login(request)
    }
}