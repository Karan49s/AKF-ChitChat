package com.karan.chit_chat.messeges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.karan.chit_chat.R
import com.karan.chit_chat.models.Message
import com.karan.chit_chat.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chatlog.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class chatlog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatlog)

        
        //supportActionBar?.setTitle(intent.getStringExtra("username"))
        val user = intent.getParcelableExtra<User>("user")
        supportActionBar?.setTitle(user.uname)
        dummydata()
        sendbtn.setOnClickListener {
            performSendMsg()
        }



    }

    private fun performSendMsg() {
        val ref = FirebaseDatabase.getInstance().getReference("/messages").push()
        val message = Message("text")
        ref.setValue(message).addOnSuccessListener {  }
    }

    private fun dummydata() {
        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        chatlog_recycler.adapter=adapter
    }
}

class ChatFromItem(val Text : String) : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.frommsg.text = Text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

}

class ChatToItem(val Text : String) : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tomsg.text = Text

    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

}