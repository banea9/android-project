package com.example.etherealartefacts.networking

import retrofit2.http.GET


interface API {
    @GET(value = "/api")
    suspend fun getRandomUser()
}