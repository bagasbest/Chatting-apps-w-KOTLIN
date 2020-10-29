package com.bagasbest.woah

import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class UserItem(private val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        //will be called in out list for each user object later on

        viewHolder.itemView.username.text = user.username

        Glide.with(viewHolder.itemView.context).load(user.profileImageUrl)
            .placeholder(R.drawable.avatar_blank).error(R.drawable.avatar_blank)
            .into(viewHolder.itemView.userImage)

    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}