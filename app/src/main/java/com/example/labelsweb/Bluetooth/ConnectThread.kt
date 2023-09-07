package com.example.labelsweb.Bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.UUID

class ConnectThread(device: BluetoothDevice):Thread() {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    private var mSocket: BluetoothSocket? = null

    init {
        try {
            Log.d("hello", "Connecting...")
            mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
            Log.d("hello", "Connected")
        } catch (e: IOException) {
            Log.d("hello", "Not Connected")
        } catch (se: SecurityException) {

        }

    }

    override fun run() {
        try {
            mSocket?.connect()
            readMessege()
        } catch (e: IOException) {

        } catch (se: SecurityException) {

        }
    }

    private fun readMessege() {
        val buffer = ByteArray(256)
        while (true){
            try {
                val length = mSocket?.inputStream?.read(buffer)
                val messege = String(buffer, 0, length ?: 0)
                Log.d("hello", messege)
            } catch (e: IOException){
                Log.d("hello", "No connected")
                break
            }
        }
    }

    fun sendMessage(message:String){
        try {
            mSocket?.outputStream?.write(message.toByteArray())
        } catch (e: IOException){
            Log.d("hello", "Message send")
        }
    }

    fun closeConnection(){
        try {
            mSocket?.close()
        } catch (e: IOException) {

        } catch (se: SecurityException) {

        }
    }
}