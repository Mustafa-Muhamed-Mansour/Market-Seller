package com.market_seller.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SplashViewModel : ViewModel()
{

    private var handler: Handler = Handler(Looper.getMainLooper())
    private var booleanLiveData: MutableLiveData<Boolean> = MutableLiveData()



    fun postDelay(): LiveData<Boolean>
    {

        handler
            .postDelayed(
                {
                     booleanLiveData.value = true

                }, 3000)

        return booleanLiveData
    }
}