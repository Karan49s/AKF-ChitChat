package com.karan.chit_chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        register.setOnClickListener {
            val email=email.text.toString()
            val pass=pass.text.toString()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener{
                    if(!it.isSuccessful)
                    {
                        Toast.makeText(this, "error",Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }


                   Toast.makeText(this, "success uid = ${it.result?.user?.uid}",Toast.LENGTH_SHORT).show()
                }
        }
    }
}