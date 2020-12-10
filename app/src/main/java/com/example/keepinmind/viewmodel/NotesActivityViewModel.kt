package com.example.keepinmind.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.keepinmind.models.NotesModel
import com.example.keepinmind.repo.AnnotationRepository

class NotesActivityViewModel(application: Application): AndroidViewModel(application) {

    private val context: Context = application.applicationContext
    private val repo = AnnotationRepository(context)

    private val mSaveResponse = MutableLiveData<Boolean>()
    val saveResponse: LiveData<Boolean> = mSaveResponse

    private val mEditModel = MutableLiveData<NotesModel>()
    val editModel: LiveData<NotesModel> = mEditModel

    fun insertAnnotation(title: String, content: String) {
        if (title.isEmpty() || title.isBlank() || content.isBlank() || content.isEmpty()){
            mSaveResponse.value = false
            return
        }

        val model = NotesModel(0, title, content)
        mSaveResponse.value = repo.insert(model) > 0
    }

    fun loadModel(id: Int) {
        mEditModel.value = repo.getById(id)
    }

    fun updateModel(model: NotesModel) {
        mSaveResponse.value = repo.update(model) > 0
    }
}