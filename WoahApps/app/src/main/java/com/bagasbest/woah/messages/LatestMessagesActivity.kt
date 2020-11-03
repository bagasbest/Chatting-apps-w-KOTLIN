package com.bagasbest.woah.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.bagasbest.woah.activity.MainActivity
import com.bagasbest.woah.R
import com.bagasbest.woah.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LatestMessagesActivity : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        title = "Latest message activity"

        fetchCurrentUser()
        verifyUserLogin()

    }

    private fun fetchCurrentUser () {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(User::class.java)
                Log.d("LatestMessageActivity", "Current user :  ${ currentUser?.profileImageUrl} ")

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
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