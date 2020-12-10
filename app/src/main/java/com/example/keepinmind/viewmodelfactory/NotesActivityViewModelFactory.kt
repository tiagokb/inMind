package com.example.keepinmind.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.keepinmind.viewmodel.NotesActivityViewModel
import java.lang.IllegalArgumentException

class NotesActivityViewModelFactory(val application: Application): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesActivityViewModel::class.java)){
            return NotesActivityViewModel(application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}