package com.karan.chit_chat.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uname: String,val piurl: String ) : Parcelable{
    constructor() : this("","")
}