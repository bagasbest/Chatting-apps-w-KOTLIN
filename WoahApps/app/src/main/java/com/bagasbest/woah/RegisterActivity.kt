package com.bagasbest.woah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {


    companion object {
        val PASSWORD_PATTERN: Pattern? =
            Pattern.compile("^" +
                    "(?=. *[0-9])" +
                    "(?=. *[a-z])" +
                    "(?=. *[A-Z])" +
                    "(?=. *[@#%^&+=])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {

            validateEmail()
            validateName()
            validatePassword()

           Log.d("RegisterActivity", "Email is : ${etEmail.text.toString()}\nNama Lengkap is : ${etName.text.toString()}\nPassword is : ${etPassword.text.toString()}\n")

        }


    }

     private fun validateEmail(): Boolean {
        val email = etEmail.text.toString().trim()

         return if(email.isEmpty()) {
             etEmail.error = "Field can't be empty"
             false;
         }else if(!EMAIL_ADDRESS.matcher(email).matches()){
             etEmail.error = "Please enter a valid email address"
             false
         }else {
             etEmail.error = null
             true
         }

    }

    private fun validateName (): Boolean {
        val name = etName.text.toString().trim()

        return if(name.isEmpty()){
            etName.error = "Field can't be empty"
            false
        }else if (name.length > 15) {
            etName.error = "Username too long"
            false
        } else {
            etName.error = null
            true
        }
    }

    private fun validatePassword (): Boolean {
        val password = etPassword.text.toString().trim()

        return if(password.isEmpty()){
            etPassword.error = "Field can't be empty"
            false
        } else if (!PASSWORD_PATTERN?.matcher(password)?.matches()!!) {
            etPassword.error = "Password too weak"
            false
        } else {
            etPassword.error = null
            true
        }

    }


    fun backLogin(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish();
    }

}