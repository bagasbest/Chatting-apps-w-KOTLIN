package com.bagasbest.woah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        Log.d("MainActivity", "Email is : $email")
        Log.d("MainActivity", "Password is : $password")

    }

    fun forgotPassword(view: View) {

    }

    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
    fun phoneLogin(view: View) {

    }
}