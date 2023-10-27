package com.example.labelsweb.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.labelsweb.Clases.AccPassValue
import com.example.labelsweb.Clases.ColorLabel
import com.example.labelsweb.Clases.DisplayState
import com.example.labelsweb.Clases.HeightLabel
import com.example.labelsweb.Clases.IPLocal
import com.example.labelsweb.Clases.Label

@Database(entities = [Label::class, HeightLabel::class, ColorLabel::class, IPLocal::class, AccPassValue::class, DisplayState::class], version = 1)
abstract class MainDb: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object{
        fun getDb(context: Context):MainDb{
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "LabelsDb.db"
            ).build()
        }
    }
}