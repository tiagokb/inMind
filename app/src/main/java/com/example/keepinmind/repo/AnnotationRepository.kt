package com.example.keepinmind.repo

import android.content.Context
import com.example.keepinmind.data.room.NotesDataBase
import com.example.keepinmind.models.NotesModel

class AnnotationRepository(context: Context) {

    private val mRemote = NotesDataBase.getIntance(context).getDao()

    //Create
    fun insert(model: NotesModel) = mRemote.insert(model)

    //Read
    fun getAll() = mRemote.getAll()

    fun getById(id: Int) = mRemote.getById(id)

    //Update
    fun update(model: NotesModel) = mRemote.update(model)

    //Delete
    fun delete(model: NotesModel) = mRemote.delete(model)
}