package com.example.labelsweb.Clases

import android.app.PendingIntent
import android.content.Context
import android.hardware.usb.UsbManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber

class IPManager( var context: Context) {
    fun getIP(permissionIntent: PendingIntent,  vm:MainViewModel): String {
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
            port.write("#".toByteArray(), 50)
            val buffer = ByteArray(30)
            val result = port.read(buffer, 200)
            val ip = String(buffer).map {
                if (it.isDigit() || it == '.') it else {
                    ""
                }
            }.joinToString("")
            if (ip.count() < 5) {
                return context.resources.getString(R.string.message4sec);
            }
            val oldIP = vm.localIP.value
            vm.localIP.value = ip
            vm.db.setIP(IPLocal(IPvalue = ip))
            vm.pageHtml.mainPage = vm.pageHtml.mainPage.replace(oldIP, ip)
            connection.close()
            return ip
        } catch (e: Exception) {

        }


        return context.resources.getString(R.string.message4sec);
    }
}