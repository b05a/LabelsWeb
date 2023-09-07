package com.example.labelsweb.Bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice

class BluetoothController(private val adapter: BluetoothAdapter) {
    private var connectThread:ConnectThread? = null
    fun connect(device: BluetoothDevice){
        if (adapter.isEnabled){
            connectThread = ConnectThread(device)
            connectThread?.start()
        }
    }
    fun sendMessage(message: String){
        connectThread?.sendMessage(message)
    }

    fun closeConnection(){
        connectThread?.closeConnection()
    }
}