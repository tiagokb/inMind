package com.example.keepinmind.data.room

import androidx.room.*
import com.example.keepinmind.models.NotesModel

@Dao
interface DAO {

    //Create
    @Insert
    fun insert(model: NotesModel): Long

    //Read
    @Query("SELECT * FROM annotation")
    fun getAll(): List<NotesModel>

    @Query("SELECT * FROM annotation WHERE id = :id")
    fun getById(id: Int): NotesModel

    //Update
    @Update
    fun update(model: NotesModel): Int

    //Delete
    @Delete
    fun delete(model: NotesModel): Int

}