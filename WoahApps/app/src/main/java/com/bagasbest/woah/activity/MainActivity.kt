package com.bagasbest.woah.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.bagasbest.woah.R
import com.bagasbest.woah.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.etEmail
import kotlinx.android.synthetic.main.activity_main.etPassword
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        val firebaseUser = FirebaseAuth.getInstance().currentUser

        //check if user is null or not
        if(firebaseUser != null) {
           startActivity(Intent(this, LatestMessagesActivity::class.java))
            finish()
        }
    }

    companion object {
        val PASSWORD_PATTERN: Pattern? =
            Pattern.compile("^" +
//                    "(?=. *[0-9])" +
//                    "(?=. *[a-z])" +
//                    "(?=. *[A-Z])" +
//                    "(?=. *[@#%^&+=])" +
//                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnLogin.setOnClickListener {

            formValidate()

        }
    }

    private fun loginAuth() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener

                //else if successfull
                Log.d("MainActivity", "Email is : ${etEmail.text.toString()}")
                Log.d("MainActivity", "Password is : ${etPassword.text.toString()}")
                Log.d("MainActivity", "UID IS : ${it.result?.user?.uid}")

                showLoader(false)
                startActivity(Intent(this, LatestMessagesActivity::class.java))
                finish()

            }
            .addOnFailureListener {
                showLoader(false)
                Log.d("MainActivity", "Failed to create user : ${it.message}")
                Toast.makeText(this, "Please enter email/password again", Toast.LENGTH_SHORT).show()
            }

    }

    private fun formValidate () {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()


        if(email.isEmpty()) etEmail.error = "Field can't be empty"
        if (password.isEmpty()) etPassword.error = "Field can't be empty"
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            etEmail.error = "Please enter a valid email address"
        if(!RegisterActivity.PASSWORD_PATTERN?.matcher(password)?.matches()!!)
            etPassword.error = "Password too weak"

        else {
            etEmail.error = null
            //Firebase Authentication to create user with email and password
            showLoader(true)
            loginAuth()
        }
    }

    fun forgotPassword(view: View) {

    }

    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
    fun phoneLogin(view: View) {

    }

    private fun showLoader (b : Boolean) {
        if(b) {
            progressBarLogin.visibility = View.VISIBLE;
        } else {
            progressBarLogin.visibility = View.GONE;
        }
    }
}