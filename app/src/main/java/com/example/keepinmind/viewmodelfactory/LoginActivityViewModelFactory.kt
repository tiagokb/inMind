package com.example.keepinmind.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.keepinmind.viewmodel.LoginActivityViewModel

class LoginActivityViewModelFactory(val application: Application): ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginActivityViewModel::class.java)){
            return LoginActivityViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}