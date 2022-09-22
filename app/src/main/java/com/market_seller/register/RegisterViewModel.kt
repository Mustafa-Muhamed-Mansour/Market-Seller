package com.market_seller.register

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.market_seller.model.SellerModel

class RegisterViewModel : ViewModel()
{

    private var booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var randomKey: String = FirebaseDatabase.getInstance().reference.push().key!!
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var sellerRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var sellerReference: StorageReference = FirebaseStorage.getInstance().reference.child("Images").child("Images-Sellers").child(randomKey)

    fun registerSeller(image: String, email: String, password: String, name: String, phone: String, address: String): LiveData<Boolean>
    {
        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful)
                {
                    sellerReference
                        .putFile(Uri.parse(image))
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful)
                            {
                                sellerReference
                                    .downloadUrl
                                    .addOnSuccessListener { img ->

                                        booleanMutableLiveData.postValue(true)

                                        val sellerModel = SellerModel(auth.uid!!, randomKey, img.toString(), email, name, phone, address)
                                        sellerRef
                                            .child("Sellers")
                                            .child(auth.uid!!)
                                            .setValue(sellerModel)

                                    }.addOnFailureListener { ex ->

                                        stringMutableLiveData.value = ex.message
                                        booleanMutableLiveData.postValue(false)
                                    }
                            }

                            else
                            {
                                stringMutableLiveData.value = task.exception!!.message
                                booleanMutableLiveData.postValue(false)
                            }
                        }
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