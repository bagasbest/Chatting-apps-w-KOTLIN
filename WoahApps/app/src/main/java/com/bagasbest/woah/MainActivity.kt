package com.bagasbest.woah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.etEmail
import kotlinx.android.synthetic.main.activity_main.etPassword
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
            validateEmail()
            validatePassword()

            Log.d("MainActivity", "Email is : ${etEmail.text.toString()}")
            Log.d("MainActivity", "Password is : ${etPassword.text.toString()}")
        }
    }

    private fun validateEmail(): Boolean {
        val email = etEmail.text.toString().trim()

        return if(email.isEmpty()) {
            etEmail.error = "Field can't be empty"
            false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.error = "Please enter a valid email address"
            false
        }else {
            etEmail.error = null
            true
        }
    }

    private fun validatePassword (): Boolean {
        val password = etPassword.text.toString().trim()

        return if(password.isEmpty()){
            etPassword.error = "Field can't be empty"
            false
        } else if (!RegisterActivity.PASSWORD_PATTERN?.matcher(password)?.matches()!!) {
            etPassword.error = "Password too weak"
            false
        } else {
            etPassword.error = null
            true
        }
    }

    fun forgotPassword(view: View) {

    }

    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
    fun phoneLogin(view: View) {

    }
}