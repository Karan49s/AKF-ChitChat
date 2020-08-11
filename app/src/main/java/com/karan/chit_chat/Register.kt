package com.karan.chit_chat

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register.setOnClickListener {
            val email=email.text.toString()
            val pass=password.text.toString()
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
        photo.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data !=null)
        {
            val uri= data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
            photo.setBackgroundDrawable(BitmapDrawable(bitmap))
        }
    }
}