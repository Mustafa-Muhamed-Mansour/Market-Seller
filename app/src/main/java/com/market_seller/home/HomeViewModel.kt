package com.market_seller.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.market_seller.model.ProductModel

class HomeViewModel : ViewModel()
{

    private var booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var mutableLiveDataProductModel: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData()
    private var productModel: ArrayList<ProductModel> = ArrayList()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var retReference: DatabaseReference = FirebaseDatabase.getInstance().reference


    fun retrieveProduct(): LiveData<ArrayList<ProductModel>>
    {
        retReference
            .child("Sellers")
            .child(auth.uid!!)
            .child("Products")
            .addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    productModel.clear()

                    if (snapshot.exists())
                    {
                        booleanMutableLiveData.postValue(true)

                        for (snap in snapshot.children)
                        {
                            val model = snap.getValue(ProductModel::class.java)
                            productModel.add(model!!)
                        }

                        mutableLiveDataProductModel.postValue(productModel)
                    }

                    else
                    {
                        booleanMutableLiveData.postValue(false)
                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    stringMutableLiveData.value = error.message
                    booleanMutableLiveData.postValue(false)
                }

            })

        return mutableLiveDataProductModel
    }


    fun deleteOfProduct(randomKey: String): LiveData<Boolean>
    {
        retReference
            .child("Sellers")
            .child(auth.uid!!)
            .child("Products")
            .child(randomKey)
            .removeValue()
            .addOnSuccessListener {

                booleanMutableLiveData.postValue(true)

            }.addOnFailureListener {

                stringMutableLiveData.value = it.message
                booleanMutableLiveData.postValue(true)
            }

        return booleanMutableLiveData
    }
}