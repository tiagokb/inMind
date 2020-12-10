package com.example.keepinmind.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.keepinmind.viewmodel.ProfileActivityViewModel
import java.lang.IllegalArgumentException
import java.util.*

class ProfileActivityViewModelFactory(val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileActivityViewModel::class.java)) {
            return ProfileActivityViewModel(application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}