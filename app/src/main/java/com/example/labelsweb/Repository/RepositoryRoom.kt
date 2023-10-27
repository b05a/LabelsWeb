package com.example.labelsweb.Repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import com.example.labelsweb.Clases.AccPassValue
import com.example.labelsweb.Clases.ColorLabel
import com.example.labelsweb.Clases.DisplayState
import com.example.labelsweb.Clases.HeightLabel
import com.example.labelsweb.Clases.IPLocal
import com.example.labelsweb.Clases.Label
import com.example.labelsweb.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryRoom(var db: MainDb) {

    // добавление новой метки
    fun setLabel(label: Label){
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().insertLabel(label)
        }
    }

    // удаление метки
    fun delLabel(label: Label){
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().deleteLabel(label)
        }
    }

    // получение списка меток
    fun getListLabel(): SnapshotStateList<Label> {
        val list = mutableStateListOf<Label>()
        for (i in db.getDao().getLabels()){
            list.add(i)
        }
        return list
    }

    fun setHeightLabel(heightLabel: HeightLabel){
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().insertHeightLabel(heightLabel)
        }
    }

    fun delHeightLabel(heightLabel: HeightLabel){
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().deleteHeightLabel(heightLabel)
        }
    }

    fun getListHeightLabel(): SnapshotStateList<HeightLabel> {
        val list = mutableStateListOf<HeightLabel>()
        for (i in db.getDao().getHeightLabel()){
            list.add(i)
        }
        return list
    }
    // установка цвета меток
    fun insertColorLabel(color:String){
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().changeColorLabel(ColorLabel(color = color))
        }
    }
    // получение цвета меток
    fun getColorLabel(): MutableState<ColorLabel> {
        var i = db.getDao().getColorLabel()
        if (i.isEmpty()){
            db.getDao().changeColorLabel(ColorLabel())
        }
        i = db.getDao().getColorLabel()
        return mutableStateOf(i[0])
    }

    // получение IP из базы данных
    fun getIP():IPLocal{
        Log.d("hello", db.getDao().dbGetIP().toString())
        var ip =db.getDao().dbGetIP()
        if (ip.isEmpty()) return IPLocal("first", "http://192.168.0.103")
        Log.d("hello", db.getDao().dbGetIP().toString())
        return db.getDao().dbGetIP().first()
    }
    // добавление IP в базу данных
    fun setIP(ip:IPLocal){
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().dbSetIp(ip)
        }
    }
    // получение аккаунта и пароля WiFi из базы данных
    fun getAccPass():AccPassValue{
        Log.d("hello", db.getDao().dbGetAccPass().toString())
        var ip =db.getDao().dbGetAccPass()
        if (ip.isEmpty()) return AccPassValue("first", "", "")
        Log.d("hello", db.getDao().dbGetAccPass().toString())
        return db.getDao().dbGetAccPass().first()
    }
    // добавление аккаунта и пароля WiFi в базу данных
    fun setAccPass(accPassValue:AccPassValue){
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().dbSetAccPass(accPassValue)
        }
    }

    fun setDisplayState(state:Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().dbSetDisplayState(DisplayState(vertical = state) )
        }
    }

    fun getDisplayState():DisplayState{
        if (db.getDao().dbGetDisplayState().isEmpty()) return DisplayState(vertical = true)
        return db.getDao().dbGetDisplayState().first()
    }

}