package com.example.labelsweb.Cases

import com.example.labelsweb.MainActivity

class DisplayType() {
    fun getTypeDisplay(activity: MainActivity): DisplayXY {
        // получаем количество dpi экрана по x
        val x: Double = activity.applicationContext.resources.displayMetrics.widthPixels/2.625
        // получаем количество dpi экрана по y
        val y: Double = activity.applicationContext.resources.displayMetrics.heightPixels/2.625
        if (x < y) return DisplayXY(x, y, true)
        return DisplayXY(x, y, false)
    }
}

data class DisplayXY(
    var x: Double,
    var y: Double,
    // если вертикальный - true, если горизонтальный - false
    var displayVertical:Boolean
)