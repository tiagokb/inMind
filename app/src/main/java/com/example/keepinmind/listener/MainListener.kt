package com.example.keepinmind.listener

import com.example.keepinmind.models.NotesModel

interface MainListener {
    fun onclick(id: Int)

    fun onLongClick(model: NotesModel)
}