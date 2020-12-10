package com.example.keepinmind.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.keepinmind.R
import com.example.keepinmind.data.FingerprintVerification
import com.example.keepinmind.viewmodel.SplashScreenActivityViewModel
import com.example.keepinmind.viewmodelfactory.SplashScreenActivityViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class SplashScreenActivity : AppCompatActivity() {

    lateinit var til: TextInputLayout
    lateinit var pin: EditText
    lateinit var fingerCheck: CheckBox
    lateinit var button: Button
    lateinit var model: SplashScreenActivityViewModel
    private var fingerVerification = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        model = ViewModelProvider(this, SplashScreenActivityViewModelFactory(application)).get(SplashScreenActivityViewModel::class.java)

        til = findViewById(R.id.splash_til_pin)
        pin = findViewById(R.id.splash_et_pin)
        fingerCheck = findViewById(R.id.splash_cb_fingerprint)
        button = findViewById(R.id.splash_btn_signup)

        isUserAlreadyRegistered()

        fingerVerification = FingerprintVerification.isAuthenticationAvaiable(this)

        if (!fingerVerification){
            fingerCheck.visibility = View.GONE
            fingerCheck.isChecked = false
        }
        observer()

        button.setOnClickListener {

            val finalPin = pin.text.toString()
            val fingerIsCheck = fingerCheck.isChecked

            model.signup(finalPin, fingerIsCheck)
        }

    }

    private fun isUserAlreadyRegistered() {
        if(model.userVerify()){
            nextActivity()
        }
    }

    private fun nextActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun observer(){
        model.signupListener.observe(this, Observer{
            if (it){
                til.isErrorEnabled = false
                makeToast("Dados Salvos!")
                nextActivity()
            } else {
                til.isErrorEnabled = true
                til.error = "O PIN deve ter 4 digitos numéricos"
            }
        })
    }

    fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}