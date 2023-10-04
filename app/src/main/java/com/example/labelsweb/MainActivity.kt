package com.example.labelsweb


import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.labelsweb.Clases.AccPassValue
import com.example.labelsweb.Clases.IPLocal
import com.example.labelsweb.Pages.MainPage
import com.example.labelsweb.ViewModel.MainViewModel
import com.example.labelsweb.ui.theme.LabelsWebTheme
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    // hello
    val vm: MainViewModel by viewModel()

    private val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"



    @SuppressLint("SourceLockedOrientationActivity")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // проверка разрешения для использования USB
        val permissionIntent =
            PendingIntent.getBroadcast(
                this,
                0,
                Intent(ACTION_USB_PERMISSION),
                PendingIntent.FLAG_IMMUTABLE
            )

        val filter = IntentFilter(ACTION_USB_PERMISSION)
        registerReceiver(usbReceiver, filter)

        // установка только вертикального экрана
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {

            LabelsWebTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                vm.displaySize(this)

                MainPage(vm, this, drawerState, scope, permissionIntent)

            }
        }
    }


    fun setAccPass(permissionIntent: PendingIntent): String {
        val manager = getSystemService(USB_SERVICE) as UsbManager
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers.isEmpty()) {
            return resources.getString(R.string.DeviceNotFound)
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
                return "unsupport setparameters"
            }
            port.read(buffer, 20)
            val result2 = String(buffer).map {
                if (it.isLetterOrDigit() || it == '.' || it == '+') it else {
                    ""
                }
            }.joinToString("")
            port.write("%${vm.acc.value}+${vm.pass.value}".toByteArray(), 50)


            var result = port.read(buffer, 200)
            val returnAccName = String(buffer).map {
                if (it.isLetterOrDigit() || it == '.' || it == '+') it else {
                    ""
                }
            }.joinToString("").split("+")

            val res = String(buffer)

            if (returnAccName[0] != vm.acc.value && returnAccName[1] != vm.pass.value) {
                return resources.getString(R.string.message4sec)
            }
            vm.accValue.value = vm.acc.value
            vm.passValue.value = vm.pass.value
            vm.db.setAccPass(AccPassValue(acc = vm.accValue.value, pass = vm.passValue.value))
            connection.close()
            return "account: ${vm.accValue.value}, password: ${vm.accValue.value}"
        } catch (e: Exception) {

        }

        return resources.getString(R.string.message4sec);
    }


    fun getIP(permissionIntent: PendingIntent): String {
        val manager = getSystemService(USB_SERVICE) as UsbManager
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers.isEmpty()) {
            return resources.getString(R.string.DeviceNotFound)
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
                return resources.getString(R.string.message4sec);
            }
            val oldIP = vm.localIP.value
            vm.localIP.value = ip
            vm.db.setIP(IPLocal(IPvalue = ip))
            vm.pageHtml.mainPage = vm.pageHtml.mainPage.replace(oldIP, ip)
            connection.close()
            return ip
        } catch (e: Exception) {

        }


        return resources.getString(R.string.message4sec);
    }




    private val usbReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ACTION_USB_PERMISSION == intent.action) {
                synchronized(this) {
                    val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        device?.apply {
                            //call method to set up device communication
                        }
                    } else {
                        Log.d("hello", "permission denied for device $device")
                    }
                }
            }
        }
    }
}