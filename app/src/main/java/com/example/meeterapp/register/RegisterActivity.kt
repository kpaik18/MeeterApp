package com.example.meeterapp.register

import TokenManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.meeterapp.R
import com.example.meeterapp.auth.`object`.AuthTokenResponse
import com.example.meeterapp.register.`object`.RegisterDTO
import com.example.meeterapp.retrofit.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class RegisterActivity : ComponentActivity() {
    val registerButton: Button = findViewById(R.id.buttonRegister)
    val retrofit = RetrofitFactory.getRetrofitClient()
    val registerService: RegisterService = retrofit.create(RegisterService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val firstName = findViewById<TextView>(R.id.registerEditTextFirstName).text.toString()
        val lastName = findViewById<TextView>(R.id.registerEditTextLastName).text.toString()
        val email = findViewById<TextView>(R.id.registerEditTextEmail).text.toString()
        val password = findViewById<TextView>(R.id.registerEditTextPassword).text.toString()
        var registerDTO = RegisterDTO(firstName, lastName, email, password)
        registerService.registerUser(registerDTO)
            .enqueue(object : Callback<AuthTokenResponse> {
                override fun onResponse(
                    call: Call<AuthTokenResponse>,
                    response: Response<AuthTokenResponse>
                ) {
                    if (response.isSuccessful) {
                        TokenManager.accessToken = response.body()!!.accessToken
                        TokenManager.refreshToken = response.body()!!.refreshToken
                        showToast("success on registration")
                    }else{
                        showToast("response not succesfull")
                    }
                }

                override fun onFailure(call: Call<AuthTokenResponse>, t: Throwable) {
                    showToast("you're such a failure")
                }
            }
            )
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}