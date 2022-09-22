package com.market_seller.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.market_seller.model.ProductModel
import com.market_seller.model.UserModel

class OrderViewModel : ViewModel()
{
    private var booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var mutableLiveDataUserModel: MutableLiveData<ArrayList<UserModel>> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var userModel: ArrayList<UserModel> = ArrayList()
    private var retReference: DatabaseReference = FirebaseDatabase.getInstance().reference


    fun retrieveUser(): LiveData<ArrayList<UserModel>>
    {
        retReference
            .child("USERS")
            .addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    userModel.clear()

                    if (snapshot.exists())
                    {
                        for (snap in snapshot.children)
                        {
                            val model = snap.getValue(UserModel::class.java)
                            userModel.add(model!!)
                        }

                        mutableLiveDataUserModel.postValue(userModel)
                        booleanMutableLiveData.postValue(true)
                    }

                    else
                    {
                        booleanMutableLiveData.postValue(false)
                        stringMutableLiveData.value = snapshot.value.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    stringMutableLiveData.value = error.message
                    booleanMutableLiveData.postValue(false)
                }

            })

        return mutableLiveDataUserModel
    }



    fun deleteUser(id: String): LiveData<Boolean>
    {
        retReference
            .child("USERS")
            .child(id)
            .removeValue()

        return booleanMutableLiveData
    }

}