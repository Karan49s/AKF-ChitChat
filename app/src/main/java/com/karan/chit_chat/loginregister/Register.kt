package com.karan.chit_chat.loginregister

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.karan.chit_chat.R
import com.karan.chit_chat.messeges.Home
import com.karan.chit_chat.models.User
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


var piurl :String = ""

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register.setOnClickListener {
            performregister()
        }
        photo.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }

        txtlogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun performregister() {
        val email=email.text.toString()
        val pass=password.text.toString()
        val uname :String =username.text.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener{
                if(!it.isSuccessful)
                {
                    Toast.makeText(this, "error",Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }


                Toast.makeText(this, "success uid = ${it.result?.user?.uid}",Toast.LENGTH_SHORT).show()
                uploadImage()
                AddUserToFirebase(uname)
            }
    }

    private fun AddUserToFirebase(uname: String) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uname, piurl)

        ref.setValue(user)
            .addOnSuccessListener{
                Log.d("Register","user saved")
                val intent = Intent(this, Home::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                Log.d("Register","user save failed")
            }

    }

    private fun uploadImage() {
        if(selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref= FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Register", "Sucessfully Uploaded Image : ${it.metadata?.path} ")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("register Activity","File Location : $it")
                    piurl = it.toString()
                }
            }
            .addOnFailureListener{
                Log.d("Register","upload image failed")
            }
    }

    var selectedPhotoUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data !=null)
        {
            selectedPhotoUri= data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)
            photo.setImageBitmap(bitmap)
        }
    }
}


