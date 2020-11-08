package com.bagasbest.woah.models

import com.bagasbest.woah.R
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_latest_message.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class LatestMessageRow(private val chatMessage: ChatMessage): Item<ViewHolder>() {
    var chatPartnerUser : User? = null

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.latest_messageTv.text = chatMessage.text

        val chatPartner:String =
            if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                chatMessage.toId
            } else {
                chatMessage.fromId
            }

        val ref = FirebaseDatabase.getInstance().getReference("users/$chatPartner")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatPartnerUser = snapshot.getValue(User::class.java)
                viewHolder.itemView.latest_username.text = chatPartnerUser?.username
                Glide.with(viewHolder.itemView.context).load(chatPartnerUser?.profileImageUrl)
                    .placeholder(R.drawable.avatar_blank).error(R.drawable.avatar_blank)
                    .into(viewHolder.itemView.latest_userImage)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })




    }

    override fun getLayout(): Int {
        return R.layout.row_latest_message
    }


}