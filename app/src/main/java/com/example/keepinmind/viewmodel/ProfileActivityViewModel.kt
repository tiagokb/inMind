package com.example.keepinmind.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.keepinmind.R
import com.example.keepinmind.data.LoginSharedPreferences
import com.example.keepinmind.models.ProfileModel

class ProfileActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    private val preferences = LoginSharedPreferences(application)

    private val mProfileResponse = MutableLiveData<Boolean>()
    val profileResponse: LiveData<Boolean> = mProfileResponse

    private val mProfileModel = MutableLiveData<ProfileModel>()
    val profileModel: LiveData<ProfileModel> = mProfileModel

    fun saveProfile(userName: String, userEmail: String, imageDrawable: Drawable?) {

        val finalName: String
        val finalEmail: String

        if (userName.isEmpty() || userName.isBlank()) {
            finalName = ""
        } else {
            finalName = userName
        }

        if (userEmail.isEmpty() || userEmail.isBlank()) {
            finalEmail = ""
        } else {
            finalEmail = userEmail
        }

        mProfileResponse.value = preferences.insertProfileData(finalName, finalEmail, imageDrawable)
    }

    fun getUserProfile() {
        mProfileModel.value = preferences.getProfileData()
    }
}