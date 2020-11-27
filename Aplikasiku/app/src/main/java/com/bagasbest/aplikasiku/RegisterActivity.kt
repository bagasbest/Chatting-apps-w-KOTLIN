package com.bagasbest.aplikasiku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val actionBar = supportActionBar;
        actionBar?.setTitle("Halaman Pendaftaran")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)



        registerBtn.setOnClickListener {
            val name = nameRegisterEt.text.toString();
            val email = emailRegisterEt.text.toString();
            val password = passwordRegisterEt.text.toString();

            var gender = "";
            if(maleRb.isChecked){
                gender = maleRb.text.toString();
            } else {
                gender = femaleRb.text.toString();
            }

            Toast.makeText(this, name + "\n" + email + "\n" + password + "\n" + gender, Toast.LENGTH_SHORT).show();
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}


