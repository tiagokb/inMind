package com.example.keepinmind.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.keepinmind.data.LoginSharedPreferences
import com.example.keepinmind.models.NotesModel
import com.example.keepinmind.models.ProfileModel
import com.example.keepinmind.repo.AnnotationRepository

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    private val context: Context = application.applicationContext
    private val mRemote = AnnotationRepository(context)

    private val preferences = LoginSharedPreferences(application)

    private val mUpdateList = MutableLiveData<Boolean>()
    val updateList: LiveData<Boolean> = mUpdateList

    private val mNewList = MutableLiveData<List<NotesModel>>()
    val newList: LiveData<List<NotesModel>> = mNewList

    private val mProfileHeader = MutableLiveData<ProfileModel>()
    val profileHeader: LiveData<ProfileModel> = mProfileHeader

    //Used in NotesFragment to update list
    fun loadList(){
        mNewList.value = mRemote.getAll()
    }

    //Used in NotesFragment to delete item from list
    fun deleteModel(model: NotesModel) {
       mUpdateList.value = mRemote.delete(model) > 0
    }

    //Used in MainActivity to update user data in header menu
    fun updateProfileHeader() {
        val model: ProfileModel = preferences.getProfileData()
        mProfileHeader.value = model
    }

}