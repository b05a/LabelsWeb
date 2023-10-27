package com.example.labelsweb.Cases

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.labelsweb.Clases.Label

class GenerationId {
    var list = mutableSetOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19)
    fun getId(listLabels: SnapshotStateList<Label>, displayStateVertical: Boolean, toastMessage: ToastMessage):Int{
        val listLabelsId = mutableSetOf<Int>()
        var e =0
        for(i in listLabels){
            listLabelsId.add(i.id)
            if (displayStateVertical == i.verticalVal) e++
            if (e>4) {toastMessage.messageInfoToMoreLabels(displayStateVertical) ; return -1}
        }

        val differenceList = list.subtract(listLabelsId)
        return differenceList.first()
    }
}