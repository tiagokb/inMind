package com.example.keepinmind.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.keepinmind.viewmodel.PostActivityViewModel

class PostActivityViewModelFactory(val application: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostActivityViewModel::class.java)){
            return PostActivityViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}