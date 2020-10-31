package com.bagasbest.woah.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import android.widget.Toast
import com.bagasbest.woah.R
import com.bagasbest.woah.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern
import kotlin.collections.HashMap


class RegisterActivity : AppCompatActivity() {


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
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {

            formValidate()
           //Log.d("RegisterActivity", "Email is : ${etEmail.text.toString()}\nNama Lengkap is : ${etName.text.toString()}\nPassword is : ${etPassword.text.toString()}\n")

        }
    }

    private fun saveUserToFirebase () {

        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        val uid = user?.uid

       // HashMap <Objects, String> hashMap = newHashMap<>()
        val hashMap : HashMap<Any, String> = HashMap()
        hashMap["username"] = etName.text.toString()
        hashMap["email"] = email.toString()
        hashMap["uid"] = uid.toString()
        hashMap["dp"] = ""

        val databaseReference = FirebaseDatabase.getInstance().getReference("/users/$uid")
        databaseReference.setValue(hashMap)
            .addOnSuccessListener {
                showLoader(false);
                Log.d("RegisterActivity", "Complete save to database")
                Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()

               val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                showLoader(false);
                Toast.makeText(this, "Trouble", Toast.LENGTH_SHORT).show()
            }
    }

     private fun formValidate() {
         val email = etEmail.text.toString().trim()
         val name = etName.text.toString().trim()
         val password = etPassword.text.toString().trim()


              if(email.isEmpty()) etEmail.error = "Field can't be empty"
              if (name.isEmpty()) etName.error = "Field can't be empty"
              if (password.isEmpty()) etPassword.error = "Field can't be empty"
              if (!EMAIL_ADDRESS.matcher(email).matches())
                  etEmail.error = "Please enter a valid email address"
              if(!PASSWORD_PATTERN?.matcher(password)?.matches()!!)
                  etPassword.error = "Password too weak"

         else {
             etEmail.error = null
              //Firebase Authentication to create user with email and password
              firebaseAuth()

         }

    }

   private fun firebaseAuth () {
       showLoader(true);
       FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
           .addOnCompleteListener {
               if(!it.isSuccessful) return@addOnCompleteListener

               //else if successfull
               Log.d("RegisterActivity", "Successfull ${it.result?.user?.uid}")

               saveUserToFirebase()
           }
           .addOnFailureListener {
               Log.d("RegisterActivity", "Failed : ${it.message}")
               Toast.makeText(this, "Please enter email/password again", Toast.LENGTH_SHORT).show()
           }
   }

    private fun showLoader (b : Boolean) {
        if(b) {
            progressBar.visibility = View.VISIBLE;
        } else {
            progressBar.visibility = View.GONE;
        }
    }

    fun backLogin(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish();
    }

}

//class User(val uid: String, val username: String, val email: String)