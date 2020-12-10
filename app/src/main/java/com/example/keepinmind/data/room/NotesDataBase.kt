package com.example.keepinmind.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.keepinmind.models.NotesModel

@Database(entities = [NotesModel::class], version = 1)
abstract class NotesDataBase : RoomDatabase() {
    abstract fun getDao(): DAO

    companion object {
        private lateinit var INSTANCE: NotesDataBase

        fun getIntance(context: Context): NotesDataBase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context, NotesDataBase::class.java, "annotationDB")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }

    }

}