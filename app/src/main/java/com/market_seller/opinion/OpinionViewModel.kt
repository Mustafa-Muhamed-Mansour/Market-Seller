package com.market_seller.opinion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.market_seller.model.OpinionModel

class OpinionViewModel : ViewModel()
{

    private var booleanMutableLiveDataOpinionModel: MutableLiveData<ArrayList<OpinionModel>> = MutableLiveData()
    private var booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var opinionModel: ArrayList<OpinionModel> = ArrayList()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var opinionReference: DatabaseReference = FirebaseDatabase.getInstance().reference


    fun opinionUser(): LiveData<ArrayList<OpinionModel>>
    {
        opinionReference
            .child("Sellers")
            .child(auth.uid!!)
            .child("Opinions")
            .addValueEventListener(object : ValueEventListener
            {

                override fun onDataChange(snapshot: DataSnapshot)
                {
                    opinionModel.clear()

                    if (snapshot.exists())
                    {
                        for (snap in snapshot.children)
                        {
                            val model = snap.getValue(OpinionModel::class.java)
                            opinionModel.add(model!!)
                        }
                        booleanMutableLiveDataOpinionModel.postValue(opinionModel)
                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    stringMutableLiveData.value = error.message
                }

            })

        return booleanMutableLiveDataOpinionModel
    }

    fun clickTrue(randomKey: String, nameUser: String, imageUser: String, commentUser: String, numberTrue: Int, numberFalse: Int): LiveData<Boolean>
    {
        val opinionModel = OpinionModel(auth.uid!!, randomKey, nameUser, imageUser, commentUser, numberTrue, numberFalse)

        opinionReference
            .child("Sellers")
            .child(auth.uid!!)
            .child("Opinions")
            .child(randomKey)
            .setValue(opinionModel)
            .addOnCompleteListener {

                if (it.isSuccessful)
                {
                    booleanMutableLiveData.postValue(true)
                }

                else
                {
                    booleanMutableLiveData.postValue(false)
                    stringMutableLiveData.value = it.exception!!.message
                }

            }

        return booleanMutableLiveData
    }

    fun clickFalse(randomKey: String, nameUser: String, imageUser: String, commentUser: String, numberTrue: Int, numberFalse: Int): LiveData<Boolean>
    {
        val opinionModel = OpinionModel(auth.uid!!, randomKey, nameUser, imageUser, commentUser, numberTrue, numberFalse)

        opinionReference
            .child("Sellers")
            .child(auth.uid!!)
            .child("Opinions")
            .child(randomKey)
            .setValue(opinionModel)
            .addOnCompleteListener {

                if (it.isSuccessful)
                {
                    booleanMutableLiveData.postValue(true)
                }

                else
                {
                    booleanMutableLiveData.postValue(false)
                    stringMutableLiveData.value = it.exception!!.message
                }

            }

        return booleanMutableLiveData
    }

}