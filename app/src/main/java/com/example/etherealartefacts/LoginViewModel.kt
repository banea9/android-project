package com.example.etherealartefacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    private val _response = MutableStateFlow<Result<LoginResponse>?>(null)
    val response : StateFlow<Result<LoginResponse>?> = _response

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val loginResponse = repository.login(request)
                    _response.value = Result.success(loginResponse)
            } catch (e: Exception) {
                _response.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}