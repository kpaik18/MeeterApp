package com.example.meeterapp.register

import TokenManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.meeterapp.R
import com.example.meeterapp.auth.`object`.AuthTokenResponse
import com.example.meeterapp.meeting.MeetingActivity
import com.example.meeterapp.register.`object`.RegisterDTO
import com.example.meeterapp.retrofit.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val registerButton: Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val retrofit = RetrofitFactory.getRetrofitClient()
        val registerService: RegisterService = retrofit.create(RegisterService::class.java)
        val firstName = findViewById<TextView>(R.id.registerEditTextFirstName).text.toString()
        val lastName = findViewById<TextView>(R.id.registerEditTextLastName).text.toString()
        val email = findViewById<TextView>(R.id.registerEditTextEmail).text.toString()
        val password = findViewById<TextView>(R.id.registerEditTextPassword).text.toString()
        if (checkValues(firstName, lastName, email, password)) {
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
                            val intent = Intent(this@RegisterActivity, MeetingActivity::class.java)
                            startActivity(intent)
                        } else {
                            showToast("Invalid Values")
                        }
                    }

                    override fun onFailure(call: Call<AuthTokenResponse>, t: Throwable) {
                        showToast("you're such a failure")
                    }
                }
                )
        }
    }

    private fun checkValues(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Boolean {
        if (firstName.length < 3) {
            showToast("First name must have at least 3 characters")
            return false
        }
        if (lastName.length < 3) {
            showToast("Last name must have at least 3 characters")
            return false
        }
        if (!Regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}\$").matches(
                email
            )
        ) {
            showToast("Enter Valid Email Address")
            return false
        }
        if (password.length < 3) {
            showToast("Password must have at least 3 characters")
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}