package com.karan.chit_chat.loginregister

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.karan.chit_chat.R
import com.karan.chit_chat.messeges.Home

lateinit var sharedPreferences: SharedPreferences
class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences =getSharedPreferences(resources.getString(R.string.states), Context.MODE_PRIVATE)


        val intent= Intent(this, Home::class.java)

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 1000)


    }
}