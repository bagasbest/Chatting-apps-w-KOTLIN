package com.bagasbest.woah.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bagasbest.woah.R
import com.bagasbest.woah.models.ChatFromItem
import com.bagasbest.woah.models.ChatToItem
import com.bagasbest.woah.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

       // val username = intent.getStringExtra(NewMessageActivity.USER_KEY)

        //pake parcelable
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        val actionBar = supportActionBar
        actionBar?.title = user?.username
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)



        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        recyclerView.adapter = adapter
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}