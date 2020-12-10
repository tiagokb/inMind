package com.example.keepinmind.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.keepinmind.R
import com.example.keepinmind.data.FingerprintVerification
import com.example.keepinmind.viewmodel.LoginActivityViewModel
import com.example.keepinmind.viewmodelfactory.LoginActivityViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var til: TextInputLayout
    private lateinit var pin: EditText
    private lateinit var fingerprint: ImageView
    private lateinit var loginBtn: Button
    private var fingerVerification = false
    private lateinit var viewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this, LoginActivityViewModelFactory(application)).get(LoginActivityViewModel::class.java)

        til = findViewById(R.id.login_til_pin)
        pin = findViewById(R.id.login_et_pin)
        fingerprint = findViewById(R.id.login_iv_fingerprint)
        loginBtn = findViewById(R.id.login_btn_login)

        fingerVerification = FingerprintVerification.isAuthenticationAvaiable(this)
        if (!fingerVerification){
            fingerprint.visibility = View.GONE
        }



        observe()
        isLoginWithFingerprintActivated(fingerVerification)

        loginBtn.setOnClickListener {
            val finalPin = pin.text.toString()
            viewModel.loginWithPin(finalPin)
        }

        fingerprint.setOnClickListener {
            isLoginWithFingerprintActivated(fingerVerification)
        }

    }

    private fun isLoginWithFingerprintActivated(fingerVerification: Boolean) {
        if (!fingerVerification){
            return
        }

        viewModel.loginWithFingerprint(this@LoginActivity)
    }

    private fun observe() {
        viewModel.loginOberver.observe(this, Observer {
            if (it){
                til.isErrorEnabled = false
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Erro ao fazer Login", Toast.LENGTH_SHORT).show()
                til.isErrorEnabled = true
            }
        })


    }


}