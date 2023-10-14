package com.example.meeterapp.register

import com.example.meeterapp.auth.`object`.AuthTokenResponse
import com.example.meeterapp.register.`object`.RegisterDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface RegisterService {
    @GET("auth/register")
    fun registerUser(
        @Body registerDTO: RegisterDTO
    ): Call<AuthTokenResponse>
}