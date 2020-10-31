package com.bagasbest.woah

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LatestMessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        title = "Latest message activity"

       verifyUserLogin()

    }


    private fun verifyUserLogin () {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_new_message -> {
                startActivity(Intent(this, NewMessageActivity::class.java))
            }
            R.id.menu_sign_out -> {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Confirm logout")
                builder.setIcon(R.drawable.logout_24)
                builder.setMessage("Are you sure want to logout?")
                builder.setCancelable(false)

                builder.setPositiveButton("YES") { _, _ ->
                FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish();
                }
                builder.setNegativeButton("NO") { _,_ ->

                }

                val alertDialog = builder.create()
                alertDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_res, menu)
        return super.onCreateOptionsMenu(menu)
    }
}