package com.example.project_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.project_android.R
import com.example.project_android.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)

        loginViewModel = LoginViewModel(this)

        btnLogin.setOnClickListener {

            if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                loginViewModel.login(username.text.toString(), password.text.toString()) { result, sessionID ->
                    if (result) {
                        Toast.makeText(this, "Log in successfully.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("sessionId", sessionID)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Login fail! Please try again!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter username and password.", Toast.LENGTH_LONG).show()
            }

        }

        btnSignup.setOnClickListener {
            Toast.makeText(this, "Going to the registration page...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}