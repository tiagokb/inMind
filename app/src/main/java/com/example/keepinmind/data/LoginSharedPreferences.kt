package com.example.keepinmind.data

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.keepinmind.models.ProfileModel
import com.example.keepinmind.models.UserOptionsModel
import com.example.keepinmind.util.StringImage

class LoginSharedPreferences(application: Application) : AppCompatActivity() {

    private val SHARED_NAME = "inmind_shared"
    private val context: Context = application.applicationContext
    private val preferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    //user settings keys
    private val KEY_PIN = "pin"
    private val KEY_FINGECHECK = "finger_check"
    //user profile keys
    private val KEY_USERNAME = "username"
    private val KEY_EMAIL = "email"
    private val KEY_STRING64_IMAGE = "user_image"

    fun insertUserData(pin: String, fingerCheck: Boolean): Boolean {
        val mRemote = preferences.edit()
        mRemote.putString(KEY_PIN, pin)
        mRemote.putBoolean(KEY_FINGECHECK, fingerCheck)
        return mRemote.commit()
    }

    fun insertProfileData(userName: String, email: String, userDrawable: Drawable?): Boolean {
        val mRemote = preferences.edit()

        mRemote.putString(KEY_USERNAME, userName)
        mRemote.putString(KEY_EMAIL, email)
        val encodedString = StringImage.drawableToString(userDrawable)
        mRemote.putString(KEY_STRING64_IMAGE, encodedString)
        return mRemote.commit()
    }

    fun getProfileData(): ProfileModel{
        val username = preferences.getString(KEY_USERNAME, "")
        val email = preferences.getString(KEY_EMAIL, "")
        val encodedImageString = preferences.getString(KEY_STRING64_IMAGE, "")
        val decodedBitmap = StringImage.string64ToBitmap(encodedImageString!!)

        val userOptionsModel = getUserData()

        val model = ProfileModel(username!!, email!!, decodedBitmap, userOptionsModel)
        return model
    }


    fun getUserData(): UserOptionsModel {
        val pin = preferences.getString(KEY_PIN, "")
        val fingerCheck = preferences.getBoolean(KEY_FINGECHECK, false)
        return UserOptionsModel(pin!!, fingerCheck)
    }

}