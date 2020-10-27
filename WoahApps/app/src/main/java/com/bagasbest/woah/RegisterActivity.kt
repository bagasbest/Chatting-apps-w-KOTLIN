package com.bagasbest.woah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val name = etName.text.toString()
            val password = etPassword.text.toString()

            Log.d("RegisterActivity", "Email is : $email\nNama Lengkap is : $name\nPassword is : $password\n")

        }


    }

    fun backLogin(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

}