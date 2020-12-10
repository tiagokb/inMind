package com.example.keepinmind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.keepinmind.data.LoginSharedPreferences

class SplashScreenActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = LoginSharedPreferences(application)

    private val mSignupListener = MutableLiveData<Boolean>()
    val signupListener: LiveData<Boolean> = mSignupListener

    fun signup(pin: String, fingerCheck: Boolean) {
        if (pin.length < 4 || pin.length > 4){
            mSignupListener.value = false
            return
        }
        preferences.insertUserData(pin, fingerCheck)
        mSignupListener.value = true
    }

    fun userVerify(): Boolean {
        val model = preferences.getUserData()
        return model.pin != ""
    }

}