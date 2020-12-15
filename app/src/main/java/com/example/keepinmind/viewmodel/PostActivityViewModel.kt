package com.example.keepinmind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.keepinmind.models.NotesModel
import com.example.keepinmind.repo.AnnotationRepository

class PostActivityViewModel(application: Application): AndroidViewModel(application) {

    private val repo = AnnotationRepository(application)

    private val mModelLoader = MutableLiveData<NotesModel>()
    val modelLoader: LiveData<NotesModel> = mModelLoader

    fun loadModel(modelId: Int) {
        mModelLoader.value = repo.getById(modelId)
    }
}