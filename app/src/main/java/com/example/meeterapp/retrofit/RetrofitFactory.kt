package com.example.meeterapp.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.meeterapp.retrofit.datedeseriliazer.LocalDateDeserializer
import com.example.meeterapp.retrofit.datedeseriliazer.LocalDateTimeDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime

class RetrofitFactory {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun getRetrofitClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://meeterbacktest.onrender.com/") // Your base URL goes here
                .client(UnsafeHttpClient.getUnsafeOkHttpClient()?.build())
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().registerTypeAdapter(
                            LocalDate::class.java,
                            LocalDateDeserializer()
                        ).registerTypeAdapter(
                            LocalDateTime::class.java,
                            LocalDateTimeDeserializer()
                        ).create()
                    )
                )
                .build()
        }
    }
}