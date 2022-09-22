package com.market_seller.addNewProduct

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.market_seller.model.ProductModel

class AddNewProductViewModel : ViewModel()
{

    private var booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var randomKey: String = FirebaseDatabase.getInstance().reference.push().key.toString()
    private var productRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var productReference: StorageReference = FirebaseStorage.getInstance().reference.child("Images").child("Images-Product").child(randomKey)


    fun addNewProduct(image: String, title: String, price: String, priceType: String, typeProduct: String, quantity: String): LiveData<Boolean>
    {

        productReference
            .putFile(Uri.parse(image))
            .addOnCompleteListener{

                if (it.isSuccessful)
                {
                    booleanMutableLiveData.postValue(true)

                    productReference
                        .downloadUrl
                        .addOnSuccessListener { img ->

                            val productModel = ProductModel(auth.uid!!, randomKey, img.toString(), title, price, priceType, typeProduct, quantity)
                            productRef
                                .child("Sellers")
                                .child(auth.uid!!)
                                .child("Products")
                                .child(randomKey)
                                .setValue(productModel)

                            booleanMutableLiveData.postValue(true)

                        }.addOnFailureListener { ex ->

                            stringMutableLiveData.value = ex.message
                            booleanMutableLiveData.postValue(false)
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