package com.example.labelsweb.Clases

import android.app.PendingIntent
import android.content.Context
import android.hardware.usb.UsbManager
import androidx.activity.ComponentActivity
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import kotlinx.coroutines.delay

class AccPassManager(var context:Context) {
    suspend fun setAccPass(permissionIntent: PendingIntent,  vm:MainViewModel) {
        val manager = context.getSystemService(ComponentActivity.USB_SERVICE) as UsbManager
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers.isEmpty()) {
//            return context.resources.getString(R.string.DeviceNotFound)
            vm.handler.post {
                vm.toastMessage.infoMessage(context.resources.getString(R.string.DeviceNotFound))
            }
            return
        }
        // Open a connection to the first available driver.
        val driver = availableDrivers[0]
        val connection =
            manager.openDevice(driver.device) // add UsbManager.requestPermission(driver.getDevice(), ..) handling here

        // проверка разрешения USB
        if (!manager.hasPermission(driver.device)) {
            // запрос разрешения USB
            manager.requestPermission(driver.device, permissionIntent)
        }


        // Most devices have just one port (port 0)
        val port = driver.ports[0]
        val buffer = ByteArray(30)
        try {
            port.open(connection)
            try {
                port.setParameters(115200, 8, 1, UsbSerialPort.PARITY_NONE)
            } catch (e: UnsupportedOperationException) {
//                return "unsupport setparameters"
                vm.handler.post {
                    vm.toastMessage.infoMessage("unsupport setparameters")
                }
                port.close()
                return
            }
            port.write("%${vm.acc.value}+${vm.pass.value}".toByteArray(), 50)

            delay(1000)

            var result = port.read(buffer, 200)
            val returnAccName = String(buffer).map {
                if (it.isLetterOrDigit() || it == '.' || it == '+') it else {
                    ""
                }
            }.joinToString("").split("+")

            val res = String(buffer)

            if (returnAccName[0] != vm.acc.value && returnAccName[1] != vm.pass.value) {
//                return context.resources.getString(R.string.message4sec)
                vm.handler.post {
                    vm.toastMessage.infoMessage(context.resources.getString(R.string.message4sec))
                }
                port.close()
                return
            }
            vm.accValue.value = vm.acc.value
            vm.passValue.value = vm.pass.value
            vm.db.setAccPass(AccPassValue(acc = vm.accValue.value, pass = vm.passValue.value))
            connection.close()
//            return "account: ${vm.accValue.value}, password: ${vm.passValue.value}"
            vm.handler.post {
                vm.toastMessage.infoMessage("account: ${vm.accValue.value}, password: ${vm.passValue.value}")
            }
            return

        } catch (e: Exception) {

        }

//        return context.resources.getString(R.string.message4sec)
        vm.handler.post {
            vm.toastMessage.infoMessage(context.resources.getString(R.string.message4sec))
        }
    }
}