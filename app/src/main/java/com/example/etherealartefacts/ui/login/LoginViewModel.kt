package com.example.etherealartefacts.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.networking.JWTTokenProvider
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DefaultRepository,
    private val jwtTokenProvider: JWTTokenProvider,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorOccurred = MutableStateFlow<Boolean?>(null)
    val errorOccurred: StateFlow<Boolean?> = _errorOccurred



    fun login(request: LoginRequest) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val loginResponse = repository.login(request)
                jwtTokenProvider.setJwtToken(loginResponse.jwt)
                _errorOccurred.value = false
            } catch (e: Exception) {
                _errorOccurred.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}