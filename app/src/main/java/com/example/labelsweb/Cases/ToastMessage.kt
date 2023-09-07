package com.example.labelsweb.Cases

import android.content.Context
import android.widget.Toast
import com.example.labelsweb.R

class ToastMessage(var context: Context) {
    fun messageInfoHorizontal(){
        Toast.makeText(context,"Для изменения настроек горизонтальных меток нужно перейти в горизонтальный режим", Toast.LENGTH_LONG).show()
    }
    fun messageInfoVertical(){
        Toast.makeText(context,"Для изменения настроек вертикальных меток нужно перейти в вертикальный режим", Toast.LENGTH_LONG).show()
    }
    fun messageInfoToMoreLabels(value:Boolean){
        var i = ""
        if (value) i= "вертикальных" else i="горизонтальных"
        Toast.makeText(context,"Слишком много $i меток, их должно быть не больше 5", Toast.LENGTH_LONG).show()
    }
    fun messageLabelNameCount(){
        Toast.makeText(context, R.string.numberLabelName, Toast.LENGTH_LONG).show()
    }
}