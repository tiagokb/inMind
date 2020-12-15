package com.example.keepinmind.listener

import com.example.keepinmind.models.NotesModel

interface MenuSelectListener {
    fun select(selectedAction: String, model: NotesModel)
}