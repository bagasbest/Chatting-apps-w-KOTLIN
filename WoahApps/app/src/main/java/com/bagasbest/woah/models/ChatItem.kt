package com.bagasbest.woah.models

import com.bagasbest.woah.R
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.messageTv
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatFromItem(val text: String, private val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.messageTv.text = text

        //load user image
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.userFromdp
        Glide.with(viewHolder.itemView.context).load(uri).placeholder(R.drawable.avatar_blank).error(R.drawable.avatar_blank)
            .into(targetImageView)
    }

    override fun getLayout(): Int {
        return  R.layout.chat_from_row
    }
}

class ChatToItem(val text: String, private val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.messageTv.text = text

        //load user image
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.userTodp
        Glide.with(viewHolder.itemView.context).load(uri).placeholder(R.drawable.avatar_blank).error(R.drawable.avatar_blank)
            .into(targetImageView)
    }

    override fun getLayout(): Int {
        return  R.layout.chat_to_row
    }
}