package com.example.meeterapp.auth

import com.example.meeterapp.auth.`object`.AuthTokenResponse
import com.example.meeterapp.auth.`object`.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/token")
    fun login(@Body loginRequest: LoginRequest): Call<AuthTokenResponse>

    @POST("auth/token/refresh")
    fun refreshAccessToken(@Body tokenRequest: AuthTokenResponse): Call<AuthTokenResponse>
}