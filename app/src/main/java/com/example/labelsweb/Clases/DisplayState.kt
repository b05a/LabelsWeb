package com.example.labelsweb.Clases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DisplayState")
data class DisplayState(
    @PrimaryKey
    var id: String = "first",
    var vertical: Boolean = true
    )