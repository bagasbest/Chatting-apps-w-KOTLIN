package com.bagasbest.aplikasiku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var cekKonsi = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        goToRegisterTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java));
        }


        loginBtn.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            if(email == "bagas@gmail.com" && password == "123"){
                //Toast.makeText(this, "YEEES BERHASIL LOGINNNN", Toast.LENGTH_SHORT).show()
                cekKonsi = true
            }else {
                ///Toast.makeText(this, "GAGAL LOGINNNN", Toast.LENGTH_SHORT).show()
            }

            val dialog = AlertDialog.Builder(this)

            if(cekKonsi) {
                dialog.setIcon(R.drawable.success)
                dialog.setTitle("Berhasil login")
                dialog.setMessage("Anda berhasil login, anda akan diarahkan ke halaman utama")
            } else {
                dialog.setIcon(R.drawable.wrong)
                dialog.setTitle("Gagal login")
                dialog.setMessage("Silahkan masukkan email dan password yang sesuai")
            }

            dialog.setPositiveButton("YES") { _,_ ->
                //masuk ke halaman utama

            }

            dialog.show()


        }


    }
}