package com.erictriawan.githubuser.ui

import android.view.ViewGroup
import android.widget.TextView
import com.erictriawan.githubuser.R
import com.erictriawan.githubuser.ui.base.BaseViewHolder
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserViewHolder(parent: ViewGroup) : BaseViewHolder<Item.UserItem>(parent, R.layout.user_item) {

    private val userName: TextView = itemView.findViewById(R.id.tv_username)
    private val avatar: CircleImageView= itemView.findViewById(R.id.imgAva)

    override fun onBind(item: Item.UserItem) {
        val user = item.user
        userName.text = user.userName
        Picasso.get().load(user.avatarUrl).placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher).into(avatar)

    }
}