package com.example.labelsweb.Clases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Labels")
data class Label (
    @PrimaryKey
    var id: Int,
    var name:String,
    var vertical:Int,
    var horizontal:Int,
    var verticalVal:Boolean
)

@Entity(tableName = "HeightLabel")
data class HeightLabel(
    @PrimaryKey
    var id: Int,
    var name: String,
    var height: Int,
    var horizontalVal:Boolean
)

@Entity(tableName = "ColorLabel")
data class ColorLabel(
    @PrimaryKey
    var id: String = "color",
    var color:String = "green"
)

@Entity(tableName = "IPLocal")
data class IPLocal(
    @PrimaryKey
    var IPnum:String = "first",
    var IPvalue:String = "http://192.168.0.103"
)

@Entity(tableName = "AccPassValue")
data class AccPassValue(
    @PrimaryKey
    var id:String = "first",
    var acc:String = "",
    var pass:String = ""
)