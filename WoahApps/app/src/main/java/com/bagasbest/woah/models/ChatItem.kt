package com.bagasbest.woah.models

import com.bagasbest.woah.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatFromItem: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return  R.layout.chat_from_row
    }
}

class ChatToItem: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return  R.layout.chat_to_row
    }
}