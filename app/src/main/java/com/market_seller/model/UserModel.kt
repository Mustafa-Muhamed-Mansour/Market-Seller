package com.market_seller.model

import android.os.Parcel
import android.os.Parcelable

class UserModel() : Parcelable
{

    lateinit var id: String
    lateinit var randomKey: String
    lateinit var image: String
    lateinit var email: String
    lateinit var name: String
    lateinit var phone: String

    constructor(parcel: Parcel) : this()
    {
        id = parcel.readString()!!
        randomKey = parcel.readString()!!
        image = parcel.readString()!!
        email = parcel.readString()!!
        name = parcel.readString()!!
        phone = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(id)
        parcel.writeString(randomKey)
        parcel.writeString(image)
        parcel.writeString(email)
        parcel.writeString(name)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel>
    {
        override fun createFromParcel(parcel: Parcel): UserModel
        {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?>
        {
            return arrayOfNulls(size)
        }
    }
}