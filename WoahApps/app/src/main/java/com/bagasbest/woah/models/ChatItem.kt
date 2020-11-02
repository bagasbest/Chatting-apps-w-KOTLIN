package com.bagasbest.woah.models

import com.bagasbest.woah.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.messageTv
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatFromItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.messageTv.text = text
    }

    override fun getLayout(): Int {
        return  R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.messageTv.text = text
    }

    override fun getLayout(): Int {
        return  R.layout.chat_to_row
    }
}