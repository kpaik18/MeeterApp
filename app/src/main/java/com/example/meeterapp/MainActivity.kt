package com.example.meeterapp

import TokenManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.meeterapp.auth.AuthService
import com.example.meeterapp.auth.`object`.AuthTokenResponse
import com.example.meeterapp.auth.`object`.LoginRequest
import com.example.meeterapp.meeting.MeetingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://meeterbacktest.onrender.com/") // Your base URL goes here
        .addConverterFactory(GsonConverterFactory.create())
        .client(UnsafeHttpClient.getUnsafeOkHttpClient()?.build())
        .build()

    val authService = retrofit.create(AuthService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TokenManager.initialize(this)
        var loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        var usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        var passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        authService.login(
            LoginRequest(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        )
            .enqueue(object : Callback<AuthTokenResponse> {
                override fun onResponse(
                    call: Call<AuthTokenResponse>,
                    response: Response<AuthTokenResponse>
                ) {
                    if (response.isSuccessful) {
                        val authTokenResponse = response.body()
                        showToast("Login Successfull")
                        authTokenResponse?.let {
                            TokenManager.accessToken = authTokenResponse!!.accessToken
                            TokenManager.refreshToken = authTokenResponse!!.refreshToken
                            val intent = Intent(this@MainActivity, MeetingActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        showToast("Invalid User Or Password")
                    }
                }

                override fun onFailure(call: Call<AuthTokenResponse>, t: Throwable) {
                    // Handle network error
                    println()
                }
            })

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private val handler = Handler()
    private val refreshTokenRunnable = object : Runnable {
        override fun run() {
            refreshToken()
            handler.postDelayed(this, 5 * 1000) // 60 seconds * 1000 milliseconds
        }
    }

    override fun onResume() {
        super.onResume()
        refreshTokenRunnable.run()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(refreshTokenRunnable)
    }

    private fun refreshToken() {
        println()
        val refreshToken = TokenManager.refreshToken
        if (!refreshToken.equals("")) {
            val refreshRequest =
                AuthTokenResponse(TokenManager.accessToken!!, TokenManager.refreshToken!!)
            authService.refreshAccessToken(
                AuthTokenResponse(
                    TokenManager.accessToken!!,
                    TokenManager.refreshToken!!
                )
            )
                .enqueue(object : Callback<AuthTokenResponse> {
                    override fun onResponse(
                        call: Call<AuthTokenResponse>,
                        response: Response<AuthTokenResponse>
                    ) {
                        if (response.isSuccessful) {
                            val authTokenResponse = response.body()
                            TokenManager.accessToken = authTokenResponse?.accessToken
                            TokenManager.refreshToken = authTokenResponse?.refreshToken
                        } else {
                            // Handle login failure
                            println()
                        }
                    }

                    override fun onFailure(call: Call<AuthTokenResponse>, t: Throwable) {
                        // Handle network error
                        println()
                    }
                })
        }

    }

}

