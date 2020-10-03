package com.karan.chit_chat.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karan.chit_chat.R
import kotlinx.android.synthetic.main.activity_about_app.*

class AboutApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        button.setOnClickListener{
            startActivity(Intent(this, Register::class.java))
            finish()
        }
    }





}