package com.karan.chit_chat.messeges

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.karan.chit_chat.R
import com.karan.chit_chat.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_newmess.view.*

class NewMessage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        fetchUsers()

    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                snapshot.children.forEach{
                    Log.d("NewMess",it.toString())
                    val user = it.getValue(User::class.java)
                    if(user!=null) adapter.add(UserItem(user))
                }
                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem
                    val intent = Intent(view.context, chatlog::class.java)
                    //intent.putExtra("username", userItem.user.uname)
                    intent.putExtra("user", userItem.user)
                    startActivity(intent)
                    finish()
                }
                newMessRecycler.adapter =adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}

class UserItem(val user : User): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.user_row_name.text = user.uname
        if(user.piurl != "")Picasso.get().load(user.piurl).into(viewHolder.itemView.user_row_img)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_newmess
    }

}