package com.market_seller.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel()
{

    private var booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginSeller(email: String, password: String): LiveData<Boolean>
    {

        auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                booleanMutableLiveData.postValue(true)


            }.addOnFailureListener{

                stringMutableLiveData.value = it.message
                booleanMutableLiveData.postValue(false)
            }

        return booleanMutableLiveData
    }

}