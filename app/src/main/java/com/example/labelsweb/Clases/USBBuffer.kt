package com.example.labelsweb.Clases

import android.app.PendingIntent
import android.content.Context
import android.hardware.usb.UsbManager
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import kotlinx.coroutines.delay

class USBBuffer(var context: Context) {
    suspend fun writeUSBBuffer(permissionIntent: PendingIntent, vm: MainViewModel, handler: Handler) {
        val manager = context.getSystemService(ComponentActivity.USB_SERVICE) as UsbManager
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers.isEmpty()) {
            return
        }
        // Open a connection to the first available driver.
        val driver = availableDrivers[0]
        val connection =
            manager.openDevice(driver.device) // add UsbManager.requestPermission(driver.getDevice(), ..) handling here

        // запрос разрешения
        manager.requestPermission(driver.device, permissionIntent)

        manager.hasPermission(driver.device)

        // Most devices have just one port (port 0)
        val port = driver.ports[0]

        try {
            port.open(connection)
            try {
                port.setParameters(115200, 8, 1, UsbSerialPort.PARITY_NONE)
            } catch (e: UnsupportedOperationException) {
                return
            }
            port.write("#".toByteArray(), 50)
            val buffer = ByteArray(30)
            delay(1000)
            val result = port.read(buffer, 200)
            val res = String(buffer).map {
                if (it.isLetterOrDigit() || it == '.' || it == '+') it else {
                    ""
                }
            }.joinToString("")
            connection.close()
            handler.post {
                Toast.makeText(context, res, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {

        }

    }

    fun getUSBBuffer(permissionIntent: PendingIntent, vm: MainViewModel): String {
        val manager = context.getSystemService(ComponentActivity.USB_SERVICE) as UsbManager
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers.isEmpty()) {
            return context.resources.getString(R.string.DeviceNotFound)
        }
        // Open a connection to the first available driver.
        val driver = availableDrivers[0]
        val connection =
            manager.openDevice(driver.device) // add UsbManager.requestPermission(driver.getDevice(), ..) handling here

        // запрос разрешения
        manager.requestPermission(driver.device, permissionIntent)

        manager.hasPermission(driver.device)

        // Most devices have just one port (port 0)
        val port = driver.ports[0]



        try {
            port.open(connection)
            try {
                port.setParameters(115200, 8, 1, UsbSerialPort.PARITY_NONE)
            } catch (e: UnsupportedOperationException) {
                return "unsupport setparameters"
            }
            val buffer = ByteArray(30)
            val result = port.read(buffer, 200)
            val res = String(buffer).map {
                if (it.isLetterOrDigit() || it == '.' || it == '+') it else {
                    ""
                }
            }.joinToString("")
            connection.close()
            return res
        } catch (e: Exception) {

        }


        return context.resources.getString(R.string.message4sec);


    }
}