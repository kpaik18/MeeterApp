package com.example.meeterapp.register

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.meeterapp.R

class RegisterActivity : ComponentActivity() {

    val registerButton: Button = findViewById(R.id.buttonRegister)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val firstName = findViewById<TextView>(R.id.registerEditTextFirstName).text
        val lastName = findViewById<TextView>(R.id.registerEditTextLastName).text
        val email = findViewById<TextView>(R.id.registerEditTextEmail).text
        val password = findViewById<TextView>(R.id.registerEditTextPassword).text

    }
}