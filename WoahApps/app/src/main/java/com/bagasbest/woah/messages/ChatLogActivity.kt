package com.bagasbest.woah.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bagasbest.woah.R
import com.bagasbest.woah.models.ChatFromItem
import com.bagasbest.woah.models.ChatMessage
import com.bagasbest.woah.models.ChatToItem
import com.bagasbest.woah.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "CHATLOG"
    }

    val adapter = GroupAdapter<ViewHolder>()
    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recyclerView.adapter = adapter

        //pake parcelable untuk mengambil data penerima
        toUser= intent.getParcelableExtra(NewMessageActivity.USER_KEY)

        val actionBar = supportActionBar
        actionBar?.title = toUser?.username
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        
        listenForMessage()
        sendBtn.setOnClickListener {
            Log.d(TAG, "Attempt to send message...")
            performSendMessage()
        }
    }

    private fun listenForMessage () {
        val reference = FirebaseDatabase.getInstance().getReference("/messages")
        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    val toId = toUser?.uid

                    if(chatMessage.fromId == FirebaseAuth.getInstance().uid && chatMessage.toId == toId) {
                        adapter.add(ChatToItem(chatMessage.text, toUser!!))
                    }else if (chatMessage.fromId == toId && chatMessage.toId == FirebaseAuth.getInstance().uid){
                        val currentUser = LatestMessagesActivity.currentUser
                        adapter.add(ChatFromItem(chatMessage.text, currentUser!!))
                    }
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })

        recyclerView.scrollToPosition(adapter.itemCount - 1)
    }



    private fun performSendMessage () {
        //send btn to add text to chat log
        val text = messageEt.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user?.uid

        if(fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId!!, System.currentTimeMillis()/1000 )
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat messages: ${reference.key}")

            }
        messageEt.text.clear()

        //to get last message (user)
        val latestMessage = FirebaseDatabase.getInstance().getReference("/latest_message/$fromId/$toId")
        latestMessage.setValue(chatMessage)

        //to get last message (other)
        val latestMessagetoRef = FirebaseDatabase.getInstance().getReference("/latest_message/$toId/$fromId")
        latestMessagetoRef.setValue(chatMessage)
    }


    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}