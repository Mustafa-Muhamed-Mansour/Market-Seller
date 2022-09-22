package com.market_seller.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.market_seller.model.SellerModel

class ProfileViewModel : ViewModel()
{

    private var booleanMutableLiveDataSellerModel: MutableLiveData<SellerModel> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var sellerRef: DatabaseReference = FirebaseDatabase.getInstance().reference


    fun retrieveSeller(): LiveData<SellerModel>
    {
        sellerRef
            .child("Sellers")
            .child(auth.uid!!)
            .addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    if (snapshot.exists())
                    {
                        val sellerModel = snapshot.getValue(SellerModel::class.java)
                        booleanMutableLiveDataSellerModel.postValue(sellerModel!!)
                    }

                    else
                    {
                        stringMutableLiveData.value = snapshot.value.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    stringMutableLiveData.value = error.message
                }

            })

        return booleanMutableLiveDataSellerModel
    }

}