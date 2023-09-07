package com.example.labelsweb.Clases

import android.content.Context
import android.hardware.usb.UsbManager
import android.util.Log
import android.widget.Toast
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber


class USBManager(context: Context) {

    // Find all available drivers from attached devices.
    var port:UsbSerialPort?=null
    val manager = context.getSystemService(Context.USB_SERVICE) as UsbManager
    val deviceList = manager.deviceList
    val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
    fun connection(context1: Context){
        Log.d("hello", availableDrivers.toString())
        if (availableDrivers.isEmpty()) {
            return;
        }
        Toast.makeText(context1,"Connection",Toast.LENGTH_LONG).show()
        val driver = availableDrivers[0]
        val connection = manager.openDevice(driver.device)
            ?: // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
            return

        port = driver.ports[0] // Most devices have just one port (port 0)

        port?.open(connection)
        port?.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
        Toast.makeText(context1,"Connection",Toast.LENGTH_LONG).show()
    }
}