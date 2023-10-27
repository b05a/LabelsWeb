package com.example.labelsweb.ViewModel

import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.os.Handler
import android.util.Log
import android.webkit.WebView
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labelsweb.Bluetooth.BluetoothController
import com.example.labelsweb.Cases.ChangeColor
import com.example.labelsweb.Cases.DisplayType
import com.example.labelsweb.Cases.DisplayXY
import com.example.labelsweb.Cases.GenerationId
import com.example.labelsweb.Cases.ToastMessage
import com.example.labelsweb.Clases.AccPassManager
import com.example.labelsweb.Clases.ColorLabel
import com.example.labelsweb.Clases.IPManager
import com.example.labelsweb.Clases.Label
import com.example.labelsweb.Clases.PageHtml
import com.example.labelsweb.Clases.USBBuffer
import com.example.labelsweb.Clases.USBManager
import com.example.labelsweb.MainActivity
import com.example.labelsweb.Repository.RepositoryRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    var displayType: DisplayType,
    var db: RepositoryRoom,
    var changeColor: ChangeColor,
    var handler: Handler,
    var toastMessage: ToastMessage,
    var generationId: GenerationId,
    var webView:WebView,
    var bluetoothController: BluetoothController,
    var bluetoothAdapter: BluetoothAdapter,
    var usbManager: USBManager,
    var pageHtml: PageHtml,
    var ipManager: IPManager,
    var accPassManager: AccPassManager,
    var usbBuffer:USBBuffer
) :
    ViewModel() {



    // параметры дисплея dpi x, dpi y и горизонтальный или вертикальный экран
    private lateinit var displayXY: DisplayXY

    // номер метки для редактирования в списке
    var changeListNumber = 0

    // текст в окне смены логина вай фай
    var acc = mutableStateOf<String>("")
    // текст в окне смены пароля вай фай
    var pass = mutableStateOf<String>("")

    // показывается окно настроек WiFi
    var wifiOption = mutableStateOf(false)

    // показывается или нет окно настроек метки
    var optionView = mutableStateOf(false)
    // диалоговое окно удаления метки
    var deleteDialogAlert = mutableStateOf(false)
    // цвет меток
    var colorLabel = mutableStateOf<ColorLabel>(ColorLabel())
    // список меток
    var listLabels = mutableStateListOf<Label>()
    // локальный IP
    var localIP = mutableStateOf("")
    // логин пароль WiFi
    var accValue = mutableStateOf("")
    var passValue = mutableStateOf("")
    // создан ли listLabels
    var listLabelsCreate = mutableStateOf(false)
    // вертикальный или горизонтальный экран показывать
    var displayStateVertical = mutableStateOf(true)


    // список меток относительной высоты
//    var listHeightLabel = mutableStateListOf<HeightLabel>()



    init {
        viewModelScope.launch(Dispatchers.IO) {

            listLabels = db.getListLabel()

            // получаем цвет меток из бд
            colorLabel = db.getColorLabel()

            accValue = mutableStateOf(db.getAccPass().acc)
            passValue = mutableStateOf(db.getAccPass().pass)
            displayStateVertical = mutableStateOf(db.getDisplayState().vertical)
            localIP = mutableStateOf(db.getIP().IPvalue)
            val ip = localIP.value
            pageHtml.mainPage = pageHtml.mainPage.replace("hello", "http://$ip")
            Log.d("hello", "IP ${pageHtml.mainPage}")
            Log.d("hello", ip)
            handler.post { webView.loadData(pageHtml.mainPage,"text/html", "en_US"); listLabelsCreate.value = true }


        }
    }

    fun changeDisplayStateVertical(){
        displayStateVertical.value = !displayStateVertical.value
        db.setDisplayState(displayStateVertical.value)
    }

    fun writeUSBBuffer(pendingIntent: PendingIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            usbBuffer.writeUSBBuffer(pendingIntent,this@MainViewModel, handler)
        }
    }

    fun getUSBBuffer(pendingIntent: PendingIntent):String{
        return usbBuffer.getUSBBuffer(pendingIntent,this)
    }
    fun getIp(pendingIntent: PendingIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            ipManager.getIP(pendingIntent, this@MainViewModel)
        }
    }

    fun setAccPass(pendingIntent: PendingIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            accPassManager.setAccPass(pendingIntent, this@MainViewModel)
        }
    }

    // получение разрешения экрана в dpi и определение вертикальный экран или горизонтальный при переключении экрана
    fun displaySize(activity: MainActivity) {
        displayXY = displayType.getTypeDisplay(activity)


        if (!optionView.value) {
            changeListNumber = -1; return
        }
        if (changeListNumber < 0) return
        if (listLabels.isEmpty()) return

//        if (!displayXY.displayVertical && listLabels[changeListNumber].verticalVal){
//            // если при повороте экрана открыто окно настроек то закрываем его и сохраняем изменения
//            if(optionView.value) optionView.value = false
//            // если при повороте экрана открыто удаления метки то закрываем его
//            if (deleteDialogAlert.value) deleteDialogAlert.value = false
//        }
//        if (displayXY.displayVertical && !listLabels[changeListNumber].verticalVal){
//            // если при повороте экрана открыто окно настроек то закрываем его и сохраняем изменения
//            if(optionView.value) optionView.value = false
//            // если при повороте экрана открыто удаления метки то закрываем его
//            if (deleteDialogAlert.value) deleteDialogAlert.value = false
//        }

    }

    // получение разрешения экрана в dpi и определение вертикальный экран или горизонтальный (в процессе работы в активити)
    fun getDisplaySize(): DisplayXY {
        return displayXY
    }
    // изменение цвета меток
    fun changeColorLabel(){
        // изменение цвета меток
        colorLabel.value = ColorLabel(color = changeColor.changeColor(colorLabel.value.color))
        // запись цвета метки в бд
        db.insertColorLabel(colorLabel.value.color)
    }
    // проверка есть ли в списке меток метки для горизонтального экрана
    fun horizontalIsEmpty():Boolean{
        if (listLabels.isEmpty()) return true
        for (i in listLabels){
            if (!i.verticalVal) return false
        }
        return true
    }
    // проверка есть ли в списке меток метки для вертикального экрана
    fun verticalIsEmpty():Boolean{
        if (listLabels.isEmpty()) return true
        for (i in listLabels){
            if (i.verticalVal) return false
        }
        return true
    }
    // добавление метки
    fun addLabel(stringResource: String) {
        // генерируем Id
        var id = generationId.getId(listLabels, displayStateVertical.value, toastMessage)
        // если превышено количество меток, завершаем функцию
        if (id < 0) return

        // добавление метки в список
        listLabels.add(Label(id, stringResource, 50, 50, displayStateVertical.value))
        db.setLabel(listLabels.last())
        // показывает панель настроек
        optionView.value = true
        // добавляет номер метки в переменную определяющую какую метку редактировать
        changeListNumber = listLabels.lastIndex
    }
    // функция начала редактирования меток вертикальных
    fun editLabelVertical(label: Label):Boolean {
        // если метка горизонтальная то сообщаем что для ее изменения нужно перейти в горизонтальный режим
        if (!displayStateVertical.value) {
            toastMessage.messageInfoVertical()
            return false
        }
        // сохраняем в бд предыдущую метку если она открыта для редактирования
        saveChange()
        // показываем панель редактирования меток
        optionView.value = true
        // добавляет номер метки в переменную определяющую какую метку редактировать
        changeListNumber = listLabels.indexOf(label)
        println(changeListNumber)
        return true
    }
    // функция начала редактирования меток горизонтальных
    fun editLabelHorizontal(label: Label): Boolean {
        // если метка горизонтальная то сообщаем что для ее изменения нужно перейти в вертикальный режим
        if (displayStateVertical.value) {
            toastMessage.messageInfoHorizontal()
            return false
        }
        // сохраняем в бд предыдущую метку если она открыта для редактирования
        saveChange()
        // показываем панель редактирования меток
        optionView.value = true
        // добавляет номер метки в переменную определяющую какую метку редактировать
        changeListNumber = listLabels.indexOf(label)
        return true
    }

    // функция для изменения координат меток
    fun changeLeft() {
        // если метка горизонтальная, то доводитим до -100, если вертикальная то до 1
        if (!displayStateVertical.value){
            if (listLabels[changeListNumber].horizontal < -100/*0.1 * displayXY.x*/) return
            val e = listLabels[changeListNumber].horizontal - 1
            listLabels[changeListNumber] = listLabels[changeListNumber].copy(horizontal = e)
        }else{
            if (listLabels[changeListNumber].horizontal < 1/*0.1 * displayXY.x*/) return
            val e = listLabels[changeListNumber].horizontal - 1
            listLabels[changeListNumber] = listLabels[changeListNumber].copy(horizontal = e)
        }

    }

    fun changeLeft10() {
        // если метка горизонтальная, то доводитим до -100, если вертикальная то до 1
        if (!displayStateVertical.value){
            if (listLabels[changeListNumber].horizontal < -100/*0.1 * displayXY.x*/) return
            val e = listLabels[changeListNumber].horizontal - 10
            listLabels[changeListNumber] = listLabels[changeListNumber].copy(horizontal = e)
        }else{
            if (listLabels[changeListNumber].horizontal < 1/*0.1 * displayXY.x*/) return
            val e = listLabels[changeListNumber].horizontal - 10
            listLabels[changeListNumber] = listLabels[changeListNumber].copy(horizontal = e)
        }
    }

    fun changeRight() {
        if (listLabels[changeListNumber].horizontal > 0.9 * displayXY.x) return
        val e = listLabels[changeListNumber].horizontal + 1
        listLabels[changeListNumber] = listLabels[changeListNumber].copy(horizontal = e)
    }

    fun changeRight10() {
        if (listLabels[changeListNumber].horizontal > 0.9 * displayXY.x) return
        val e = listLabels[changeListNumber].horizontal + 10
        listLabels[changeListNumber] = listLabels[changeListNumber].copy(horizontal = e)
    }

    fun changeUp() {
        if (listLabels[changeListNumber].vertical < 1/*0.1 * displayXY.y*/) return
        val e = listLabels[changeListNumber].vertical - 1
        listLabels[changeListNumber] = listLabels[changeListNumber].copy(vertical = e)
    }

    fun changeUp10() {
        if (listLabels[changeListNumber].vertical < 1/*0.1 * displayXY.y*/) return
        val e = listLabels[changeListNumber].vertical - 10
        listLabels[changeListNumber] = listLabels[changeListNumber].copy(vertical = e)
    }

    fun changeDown() {
        if (listLabels[changeListNumber].vertical > 0.9 * displayXY.y) return
        val e = listLabels[changeListNumber].vertical + 1
        listLabels[changeListNumber] = listLabels[changeListNumber].copy(vertical = e)
    }

    fun changeDown10() {
        if (listLabels[changeListNumber].vertical > 0.9 * displayXY.y) return
        val e = listLabels[changeListNumber].vertical + 10
        listLabels[changeListNumber] = listLabels[changeListNumber].copy(vertical = e)
    }

    // функция изменения названия метки
    fun changeNameLabel(name: String) {
        // если количество символов в названии метки больше 11 то не добавляем символы
        if (name.count() > 11) {
            toastMessage.messageLabelNameCount()
            return
        }
        listLabels[changeListNumber] = listLabels[changeListNumber].copy(name = name)
    }

    // сохранение метки
    fun saveChange() {
        // если если неизвестен номер метки для редактирования то завершаем функцию
        if (changeListNumber < 0) return
        // сохраняем метку в бд
        db.setLabel(listLabels[changeListNumber])
    }

    // удаление метки
    fun delLabel() {
        // убираем диалоговое окно удаления метки
        deleteDialogAlert.value = !deleteDialogAlert.value;
        // убираем окно настройки меток
        optionView.value = false

        db.delLabel(listLabels[changeListNumber])

        // удаляем метку из списка меток
        listLabels.removeAt(changeListNumber)
    }

}