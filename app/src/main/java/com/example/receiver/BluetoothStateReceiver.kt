package com.example.receiver

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BluetoothStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        val device = intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        when (action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                Log.d("BluetoothReceiver", "Device connected: ${device?.name}")
                // Trigger auto profile load for this device
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                Log.d("BluetoothReceiver", "Device disconnected: ${device?.name}")
                // Restore default profile
            }
        }
    }
}
