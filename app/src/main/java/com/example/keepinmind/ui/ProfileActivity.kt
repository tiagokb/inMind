package com.example.keepinmind.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.keepinmind.R
import com.example.keepinmind.listener.IntentPickListener
import com.example.keepinmind.ui.fragments.IntentDialogFragment
import com.example.keepinmind.viewmodel.ProfileActivityViewModel
import com.example.keepinmind.viewmodelfactory.ProfileActivityViewModelFactory


class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imageProfile: ImageView
    private lateinit var loadImageText: TextView
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var saveBtn: Button

    private var REQUEST_CODE: Int = 0

    private lateinit var viewModel: ProfileActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        viewModel = ViewModelProvider(this, ProfileActivityViewModelFactory(application))
            .get(ProfileActivityViewModel::class.java)

        imageProfile = findViewById(R.id.profile_iv)
        loadImageText = findViewById(R.id.profile_tv_load_image)
        username = findViewById(R.id.profile_et_username)
        email = findViewById(R.id.profile_et_email)
        saveBtn = findViewById(R.id.profile_btn_save)

        observe()
        isUserRegistered()
        saveBtn.setOnClickListener(this)
        loadImageText.setOnClickListener(this)
    }

    private fun isUserRegistered(){
        viewModel.getUserProfile()
    }

    private fun observe() {
        viewModel.profileResponse.observe(this, Observer {
            if (it) {
                makeToast("Perfil atualizado")
                finish()
            } else {
                makeToast("Erro ao atualizar perfil!")
            }
        })

        viewModel.profileModel.observe(this, Observer {
            if (it.userName != "") {
                username.setText(it.userName)
            }

            if (it.userEmail != "") {
                email.setText(it.userEmail)
            }

            if (it.bitmap != null) {
                imageProfile.setImageBitmap(it.bitmap)
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.profile_btn_save -> saveProfile()
            R.id.profile_tv_load_image -> onImageIntentClick()
        }
    }

    private fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun onImageIntentClick(){
        intentPick()
    }

    private fun intentLoadImageFromGaleryOrTakeAShot() {
        when (REQUEST_CODE){
            1 -> openCamera()
            2 -> openGallery()
            else -> makeToast("Tente novamente")
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        val pickIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(intent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, 2)
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != RESULT_OK || data == null){
            makeToast("Algum erro ocorreu, por favor tente novamente")
            return
        }

        when (requestCode){

            2 -> {
                val inputStream = data.data?.let { contentResolver.openInputStream(it) }
                val bm = BitmapFactory.decodeStream(inputStream)
                imageProfile.setImageBitmap(bm)
            }
            1 -> {
                val bundle = data.extras
                val bitmap = bundle?.let { it.get("data") } as Bitmap
                imageProfile.setImageBitmap(bitmap)
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun intentPick(){
        val dialogFragment = IntentDialogFragment(object : IntentPickListener {
            override fun actionPick(requestCode: Int) {
                REQUEST_CODE = requestCode
                intentLoadImageFromGaleryOrTakeAShot()
            }
        })

        dialogFragment.show(supportFragmentManager, "intentFragment")
    }


    private fun saveProfile() {
        val finalName = username.text.toString()
        val finalEmail = email.text.toString()
        val finalImage = imageProfile.drawable

        viewModel.saveProfile(finalName, finalEmail, finalImage)
    }

}