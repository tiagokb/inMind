package com.example.keepinmind.models

import android.graphics.Bitmap

data class ProfileModel(
    var userName: String,
    var userEmail: String,
    var bitmap: Bitmap?,
    var userSettings: UserOptionsModel
)