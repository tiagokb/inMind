package com.example.keepinmind.viewmodel

import android.app.Application
import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.keepinmind.data.LoginSharedPreferences
import java.util.concurrent.Executor

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = application.applicationContext
    private val preference: LoginSharedPreferences = LoginSharedPreferences(application)

    private val mLoginObserver = MutableLiveData<Boolean>()
    val loginOberver: LiveData<Boolean> = mLoginObserver

    fun loginWithPin(pin: String) {

        if (pin.length != 4) {
            mLoginObserver.value = false
            return
        }

        val model = preference.getUserData()
        val modelPin = model.pin
        if (modelPin != pin) {
            mLoginObserver.value = false
            return
        }

        mLoginObserver.value = true
    }

    fun loginWithFingerprint(fragmentActivity: FragmentActivity) {
        //Executor
        val executor: Executor = ContextCompat.getMainExecutor(context)

        //Biometric prompt
        val biometricPrompt = BiometricPrompt(fragmentActivity, executor, object :
            BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

                mLoginObserver.value = false
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                mLoginObserver.value = false
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                mLoginObserver.value = true
            }

        })

        //Biometric prompt INFO
        val info: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticação biométrica")
            .setSubtitle("Aproxime o seu dedo do leitor")
            .setNegativeButtonText("cancel")
            .build()

        biometricPrompt.authenticate(info)
    }
}