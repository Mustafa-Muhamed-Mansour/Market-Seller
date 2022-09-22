package com.market_seller.showOrder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.market_seller.model.OrderModel
import com.market_seller.model.UserModel

class ShowOrderViewModel : ViewModel()
{
    private var booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var mutableLiveDataOrderModel: MutableLiveData<ArrayList<OrderModel>> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var orderModel: ArrayList<OrderModel> = ArrayList()
    private var retReference: DatabaseReference = FirebaseDatabase.getInstance().reference



    fun retrieveOrderOfUser(id: String): LiveData<ArrayList<OrderModel>>
    {
        retReference
            .child("USERS")
            .child(id)
            .child("Orders")
            .addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    orderModel.clear()

                    if (snapshot.exists())
                    {
                        for (snap in snapshot.children)
                        {
                            val model = snap.getValue(OrderModel::class.java)
                            orderModel.add(model!!)
                        }

                        mutableLiveDataOrderModel.postValue(orderModel)
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

        return mutableLiveDataOrderModel
    }


    fun deleteOrder(id: String, randomKey: String): LiveData<Boolean>
    {
        retReference
            .child("USERS")
            .child(id)
            .child("Orders")
            .child(randomKey)
            .removeValue()
            .addOnCompleteListener {

                if (it.isSuccessful)
                {
                    booleanMutableLiveData.postValue(true)
                }

                else
                {
                    stringMutableLiveData.value = it.exception!!.message
                    booleanMutableLiveData.postValue(false)
                }
            }

        return booleanMutableLiveData
    }

}