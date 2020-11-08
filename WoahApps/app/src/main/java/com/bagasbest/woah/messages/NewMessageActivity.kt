package com.bagasbest.woah.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bagasbest.woah.R
import com.bagasbest.woah.models.User
import com.bagasbest.woah.models.UserItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        val actionbar = supportActionBar
        actionbar!!.title = "Start new message"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayShowHomeEnabled(true)

        fetchUserFromFirebase()

    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUserFromFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("/users")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val adapter = GroupAdapter<ViewHolder>()

                snapshot.children.forEach {

                    val user = it.getValue(User::class.java)
                    val myId = FirebaseAuth.getInstance().uid
                    if(user != null) {

                        //add every user except user id
                        if(user.uid != myId){
                            adapter.add(UserItem(user))
                        }

                    }

                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent  = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                }

                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}