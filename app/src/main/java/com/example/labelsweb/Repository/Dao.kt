package com.example.labelsweb.Repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labelsweb.Clases.AccPassValue
import com.example.labelsweb.Clases.ColorLabel
import com.example.labelsweb.Clases.DisplayState
import com.example.labelsweb.Clases.HeightLabel
import com.example.labelsweb.Clases.IPLocal
import com.example.labelsweb.Clases.Label

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLabel(label: Label)

    @Query("SELECT * FROM Labels")
    fun getLabels():List<Label>

    @Delete
    fun deleteLabel(label: Label)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeightLabel(heightLabel: HeightLabel)

    @Query("SELECT * FROM HeightLabel")
    fun getHeightLabel():List<HeightLabel>

    @Delete
    fun deleteHeightLabel(label: HeightLabel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun changeColorLabel(color: ColorLabel)

    @Query("SELECT * FROM ColorLabel")
    fun getColorLabel():List<ColorLabel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun dbSetIp(ip:IPLocal)

    @Query("SELECT * FROM IPLocal")
    fun dbGetIP():List<IPLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun dbSetAccPass(accPass:AccPassValue)

    @Query("SELECT * FROM AccPassValue")
    fun dbGetAccPass():List<AccPassValue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun dbSetDisplayState(displayState:DisplayState)

    @Query("SELECT *FROM DisplayState")
    fun dbGetDisplayState():List<DisplayState>
}