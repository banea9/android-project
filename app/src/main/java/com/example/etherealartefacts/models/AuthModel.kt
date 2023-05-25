package com.example.etherealartefacts.models

data class LoginRequest(
    val identifier: String,
    val password: String,
)

data class LoginResponse(
    val jwt: String,
    val user: User
)

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val provider: String,
    val confirmed: Boolean,
    val blocked: Boolean
)